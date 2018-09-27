package com.pcsb.rig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.ToggleEvent;
import org.primefaces.event.timeline.TimelineAddEvent;
import org.primefaces.event.timeline.TimelineSelectEvent;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

import com.pcsb.rig.bo.RigPlanBo;
import com.pcsb.rig.model.Contract;
import com.pcsb.rig.model.Rig;
import com.pcsb.rig.model.RigPlan;
import com.pcsb.rig.model.Timeline;

@ManagedBean(value = "rigPlanBean")
@SessionScoped
public class RigPlanBean {

	@Inject
	RigPlanBo rigPlanBo;

	private TimelineModel model;
	private Timeline timelineObj;
	private TimelineEvent event;
	private RigPlan rigPlanObj;
	private List<Rig> rig;
	private List<Contract> contract;
	private Date start;
	private Date end;
	private String rigType;
	private String wellType;
	private String block;
	private String status;
	private List<HashMap<String, Object>> timelineMapObj;
	private List<HashMap<String,Object>> mainTimelineObj;
	private HashMap<String,Object> mainMapObj;

	public void setRigPlanBo(RigPlanBo rigPlanBo) {
		this.rigPlanBo = rigPlanBo;
	}

	public void init() {
		try {

			
			mainTimelineObj = new ArrayList<>();
			rig = rigPlanBo.findAllRig();
			contract = rigPlanBo.findAllContract();

			for (Contract c : contract) {
				System.out.println(c.getContract_status());
				timelineMapObj = new ArrayList<>();
				mainMapObj = new HashMap<>();
				for (Rig r : rig) {
					getTimelineDetails(r.getRig_typ(), this.getWellType(), this.getBlock(), this.getStatus(),
							c.getContract_status());
					mainMapObj.put("contract", c.getContract_status());
					mainMapObj.put("rigLine",getTimelineMapObj());
				}
				mainTimelineObj.add(mainMapObj);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public TimelineModel getModel() {
		return model;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}

	/**
	 * add days to date in java
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);

		return cal.getTime();
	}

	public void handleToggle(ToggleEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Toggled",
				"Visibility:" + event.getVisibility());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onSelect(TimelineSelectEvent e) {

		TimelineEvent timelineEvent = e.getTimelineEvent();
		setTimelineObj((Timeline) timelineEvent.getData());

		setRigPlanObj(rigPlanBo.findRigPlanById(timelineObj.getId()));

	}

	public void findAllRig() {
		setRig(rigPlanBo.findAllRig());
	}

	public List<Rig> getRig() {
		return rig;
	}

	public void setRig(List<Rig> rig) {
		this.rig = rig;
	}

	public Timeline getTimelineObj() {
		return timelineObj;
	}

	public void setTimelineObj(Timeline timelineObj) {
		this.timelineObj = timelineObj;
	}

	public RigPlan getRigPlanObj() {
		return rigPlanObj;
	}

	public void setRigPlanObj(RigPlan rigPlanObj) {
		this.rigPlanObj = rigPlanObj;
	}

	public void saveRigPlan() {

		// save to db
		rigPlanBo.saveRigPlan(rigPlanObj);

		List<RigPlan> planList = null;
		List<RigPlan> rigPlan;
		RigPlan rigPlanTemp = rigPlanObj;

		do {
			rigPlan = rigPlanBo.findRigPlanMove(rigPlanTemp);

			if (rigPlan != null && rigPlan.size() > 0) {
				if (rigPlan.get(0).getRig_up() != null && rigPlanTemp.getRig_down() != null) {
					rigPlan.get(0).setRig_up(addDays(rigPlanTemp.getRig_down(), 1));
					rigPlan.get(0).setStart_date((addDays(rigPlan.get(0).getRig_up(), 1)));
				} else if (rigPlan.get(0).getRig_up() != null) {
					rigPlan.get(0).setRig_up(addDays(rigPlanTemp.getEnd_date(), 1));
					rigPlan.get(0).setStart_date((addDays(rigPlan.get(0).getRig_up(), 1)));
				} else {
					rigPlan.get(0).setStart_date(addDays(rigPlanTemp.getEnd_date(), 1));
				}

				rigPlan.get(0).setEnd_date(addDays(rigPlan.get(0).getStart_date(), rigPlan.get(0).getPlan_days()));

				if (rigPlan.get(0).getRig_down() != null) {
					rigPlan.get(0).setRig_down(addDays(rigPlan.get(0).getEnd_date(), 1));
				}

				rigPlanBo.saveRigPlan(rigPlan.get(0));
				planList = rigPlanBo.findRigPlanMoveList(rigPlan.get(0));
				rigPlanTemp = rigPlan.get(0);
			}

			if (rigPlan.size() > 0) {
				planList = rigPlanBo.findRigPlanMoveList(rigPlan.get(0));
				rigPlanTemp = rigPlan.get(0);
			}

		} while (planList != null && planList.size() > 0);

		if (rigPlan != null && rigPlan.size() > 0) {
			searchRigPlan(rigPlan.get(0).getRig_typ(), "select", "select", "select");
		} else {
			searchRigPlan("select", "select", "select", "select");
		}

	}

	public TimelineEvent getEvent() {
		return event;
	}

	public void setEvent(TimelineEvent event) {
		this.event = event;
	}

	public void searchRigPlan(String rigTyp, String wellTyp, String block, String status) {

		System.out.println("search for rig type" + rigTyp);

		if (rigTyp.equalsIgnoreCase("select") && wellTyp.equalsIgnoreCase("select") && block.equalsIgnoreCase("select")
				&& status.equalsIgnoreCase("select")) {
			init();
		} else if (rigTyp.equalsIgnoreCase("select")) {
			init();
		} else {
			
			mainTimelineObj = new ArrayList<>();
			contract = rigPlanBo.findAllContract();

			for (Contract c : contract) {
				mainMapObj = new HashMap<>();
				timelineMapObj = new ArrayList<>();
				getTimelineDetails(rigTyp, wellTyp, block, status, c.getContract_status());
				mainMapObj.put("contract", c.getContract_status());
				mainMapObj.put("rigLine",getTimelineMapObj());
				mainTimelineObj.add(mainMapObj);
			}
		}

	}

	public String getRigType() {
		return rigType;
	}

	public void setRigType(String rigType) {
		this.rigType = rigType;
	}

	public List<HashMap<String, Object>> getTimelineMapObj() {
		return timelineMapObj;
	}

	public void setTimelineMapObj(List<HashMap<String, Object>> timelineMapObj) {
		this.timelineMapObj = timelineMapObj;
	}

	public void getTimelineDetails(String rig, String well, String block, String status, String contract_stat) {
		try {

			HashMap<String, Object> modelMap = new HashMap<String, Object>();
			Date now = new Date();
			List<RigPlan> rpObjList = rigPlanBo.findByRigCriteria(rig, well, block, status, contract_stat);
			String startS = null;
			String endS = null;
			String style = null;

			// create timeline model
			model = new TimelineModel();

			for (RigPlan rpObj : rpObjList) {
				now = new Date();
				Date end = new Date(now.getTime() - 12 * 60 * 60 * 1000 * 7);
				Date start;

				for (int i = 0; i < 1; i++) {
					if (rpObj.getRig_up() != null) {
						startS = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rpObj.getRig_up());
						endS = new SimpleDateFormat("dd/MM/yyyy").format(rpObj.getStart_date());

						start = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(startS);
						end = new SimpleDateFormat("dd/MM/yyyy").parse(endS);

						// long r = Math.round(Math.random() * 2);
						String rigUp = "Rig Up";

						// create an event with content, start / end dates, editable flag, group name
						// and custom style class
						event = new TimelineEvent(
								new Timeline(rpObj.getRp_id(), rpObj.getRig_typ(), rpObj.getRig_name(), rigUp), start,
								end, false, rpObj.getRig_name(), "unavailable".toLowerCase());
						model.add(event);
					}

					startS = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rpObj.getStart_date());
					endS = new SimpleDateFormat("dd/MM/yyyy").format(rpObj.getEnd_date());

					start = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(startS);
					end = new SimpleDateFormat("dd/MM/yyyy").parse(endS);

					// long r = Math.round(Math.random() * 2);
					String wellname = rpObj.getWell_name();
					if (rpObj.getWell_typ().equalsIgnoreCase("dev")) {
						style = rpObj.getBlock() + "_" + rpObj.getWell_typ();
					} else {
						style = rpObj.getWell_typ();
					}

					// create an event with content, start / end dates, editable flag, group name
					// and custom style class
					event = new TimelineEvent(
							new Timeline(rpObj.getRp_id(), rpObj.getRig_typ(), rpObj.getRig_name(), wellname), start,
							end, false, rpObj.getRig_name(), style.toLowerCase());
					model.add(event);

					if (rpObj.getRig_down() != null) {
						startS = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rpObj.getEnd_date());
						endS = new SimpleDateFormat("dd/MM/yyyy").format(rpObj.getRig_down());

						start = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(startS);
						end = new SimpleDateFormat("dd/MM/yyyy").parse(endS);

						// long r = Math.round(Math.random() * 2);
						String rigDown = "Rig Down";

						// create an event with content, start / end dates, editable flag, group name
						// and custom style class
						event = new TimelineEvent(
								new Timeline(rpObj.getRp_id(), rpObj.getRig_typ(), rpObj.getRig_name(), rigDown), start,
								end, false, rpObj.getRig_name(), "unavailable".toLowerCase());
						model.add(event);
					}

				}
				modelMap.put("rigtype", rig);
				modelMap.put("model", model);
			}
			if (rpObjList != null && rpObjList.size() > 0) {
				timelineMapObj.add(modelMap);
				System.out.println("timeline Obj"+timelineMapObj);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onAdd(TimelineAddEvent e) {
		// get TimelineEvent to be added
		rigPlanObj = new RigPlan();
		event = new TimelineEvent(new RigPlan(), e.getStartDate(), e.getEndDate(), true, e.getGroup());

		// add the new event to the model in case if user will close or cancel the "Add
		// dialog"
		// without to update details of the new event. Note: the event is already added
		// in UI.
		model.add(event);
	}

	public String getWellType() {
		return wellType;
	}

	public void setWellType(String wellType) {
		this.wellType = wellType;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void loadPage() {
		this.setRigType("select");
		this.setWellType("select");
		this.setBlock("select");
		this.setStatus("select");
		init();
	}

	public List<Contract> getContract() {
		return contract;
	}

	public void setContract(List<Contract> contract) {
		this.contract = contract;
	}

	public List<HashMap<String, Object>> getMainTimelineObj() {
		return mainTimelineObj;
	}

	public void setMainTimelineObj(List<HashMap<String, Object>> mainTimelineObj) {
		this.mainTimelineObj = mainTimelineObj;
	}

	public HashMap<String, Object> getMainMapObj() {
		return mainMapObj;
	}

	public void setMainMapObj(HashMap<String, Object> mainMapObj) {
		this.mainMapObj = mainMapObj;
	}
}