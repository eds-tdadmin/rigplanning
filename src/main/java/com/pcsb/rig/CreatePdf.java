package com.pcsb.rig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pcsb.rig.bo.RigPlanBo;
import com.pcsb.rig.model.Contract;
import com.pcsb.rig.model.Printer;
import com.pcsb.rig.model.Rig;
import com.pcsb.rig.model.RigPlan;

@ManagedBean(value = "createReport")
@SessionScoped
public class CreatePdf {

	@Inject
	RigPlanBo rigPlanBo;

	public static final String DEST = "C:\\Users\\ACER\\Desktop\\EFTECH\\EFTECH PROJECT\\RigPlanningReportPDF\\ReportPDF\\Report PDF.pdf";
	public static final String months[] = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV",
			"DEC" };
	public static final int hours[] = {};
	public static Date date = new Date();
	Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
	// get year before
	@SuppressWarnings("deprecation")
	public static Integer yearBefore = (new Date().getYear() + 1900) - 1;
	public static String yearBeforeString = yearBefore.toString();
	// get current year
	@SuppressWarnings("deprecation")
	public static Integer yearCurrent = new Date().getYear() + 1900;
	public static String yearCurrentString = yearCurrent.toString();
	// get year after
	@SuppressWarnings("deprecation")
	public static Integer yearAfter = (new Date().getYear() + 1900) + 1;
	public static String yearAfterString = yearAfter.toString();

	private String wellType;
	private String block;
	private String status;
	private Printer printer;
	private List<Contract> contract;
	private HashMap<String, Object> mainMapObj;
	private List<HashMap<String, Object>> timelineMapObj;
	private List<HashMap<String, Object>> mainTimelineObj;
	private List<Rig> rig;
	private List<Printer> printerList;
	private List<HashMap<String, Object>> timelineMapObjPrinter;

	private RigPlanBean rigPlanBean;

	public static void main(String[] args) throws Throwable {
		File file = new File(DEST);

		file.getParentFile().mkdirs();
		// new CreateReport().mainTable();
	}

	public void mainTable(String rig_typ, String well_type, String block, String status)
			throws Throwable, DocumentException {
		Document document = new Document(PageSize.A3.rotate(), 10f, 10f, 100f, 10f);
		PdfWriter.getInstance(document, new FileOutputStream(DEST));
		document.open();

		// main table
		PdfPTable mainTable = new PdfPTable(1);
		mainTable.getDefaultCell().setPadding(0);
		mainTable.setTotalWidth(1115);
		mainTable.setLockedWidth(true);

		mainTable.addCell(Header());
		mainTable.addCell(RigInfo(rig_typ, well_type, block, status));
		document.add(mainTable);
		document.close();
	}

	@SuppressWarnings("deprecation")
	public PdfPTable Header() throws IOException, DocumentException {

		// table header
		PdfPTable header = new PdfPTable(5);
		header.setWidths(new int[] { 100, 365, 365, 365, 100 });
		PdfPCell cell;
		cell = new PdfPCell(new Phrase("Confidential"));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		header.addCell(cell);
		cell = new PdfPCell(
				new Phrase(" WELLS WORKING STDS(MALAYSIA) - FOR PLANNING RESOURCES, EQUIPMENT & MATERIALS "));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(3);
		header.addCell(cell);
		cell = new PdfPCell(new Phrase(
				"REV : " + date.getDay() + "." + date.getMonth() + "." + (date.getYear() + +1900) + "", font));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		header.addCell(cell);
		cell = new PdfPCell(new Phrase(yearBeforeString));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		header.addCell(cell);
		cell = new PdfPCell(new Phrase(yearCurrentString));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		header.addCell(cell);
		cell = new PdfPCell(new Phrase(yearAfterString));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		header.addCell(cell);

		// subtable month for year before
		PdfPTable tableMonthYearBefore = new PdfPTable(12);
		tableMonthYearBefore.setWidths(new int[] { 31, 30, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 });
		for (int monthYearBefore = 0; monthYearBefore < 12; monthYearBefore++) {
			PdfPCell monthBeforeCell = new PdfPCell(new Phrase(months[monthYearBefore], font));
			monthBeforeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableMonthYearBefore.addCell(monthBeforeCell);
			monthBeforeCell.setColspan(4);
		}

		// subtable month for year current
		PdfPTable tableMonthYearCurrent = new PdfPTable(12);
		tableMonthYearCurrent.setWidths(new int[] { 31, 30, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 });
		for (int monthYearCurrent = 0; monthYearCurrent < 12; monthYearCurrent++) {
			PdfPCell monthCurrentCell = new PdfPCell(new Phrase(months[monthYearCurrent], font));
			monthCurrentCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableMonthYearCurrent.addCell(monthCurrentCell);
		}

		// subtable month for year after
		PdfPTable tableMonthYearAfter = new PdfPTable(12);
		tableMonthYearAfter.setWidths(new int[] { 31, 30, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 });
		for (int monthYearAfter = 0; monthYearAfter < 12; monthYearAfter++) {
			PdfPCell monthAfterCell = new PdfPCell(new Phrase(months[monthYearAfter], font));
			monthAfterCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableMonthYearAfter.addCell(monthAfterCell);
		}

		// insert subtable in cell
		PdfPCell tableMonthYearBeforeCell = new PdfPCell(tableMonthYearBefore);
		PdfPCell tableMonthYearCurrentCell = new PdfPCell(tableMonthYearCurrent);
		PdfPCell tableMonthYearAfterCell = new PdfPCell(tableMonthYearAfter);

		// insert cell months in header table
		header.addCell(" JACK UP (contracted) ");
		header.addCell(tableMonthYearBeforeCell);
		header.addCell(tableMonthYearCurrentCell);
		header.addCell(tableMonthYearAfterCell);
		header.addCell(" ");

		return header;
	}

	public PdfPTable RigInfo(String rig_typ, String well_type, String block, String status) throws DocumentException {
		PdfPTable rigInfoTable = new PdfPTable(5);
		rigInfoTable.setWidths(new int[] { 100, 365, 365, 365, 100 });

		rigInfoTable.addCell(" ");
		rigInfoTable.addCell(RigYearBefore(rig_typ, well_type, block, status));
		rigInfoTable.addCell("JACK UP");
		rigInfoTable.addCell("JACK UP");
		rigInfoTable.addCell(" ");

		return rigInfoTable;
	}

	public PdfPCell RigYearBefore(String rigTyp, String wellTyp, String block, String status) throws DocumentException {

		PdfPTable tableRigYearBefore = new PdfPTable(365);
		tableRigYearBefore.setWidthPercentage(30);

		if (rigTyp.equalsIgnoreCase("select") && wellTyp.equalsIgnoreCase("select") && block.equalsIgnoreCase("select")
				&& status.equalsIgnoreCase("select")) {
			initPdf();
		} else if (rigTyp.equalsIgnoreCase("select")) {
			initPdf();
		} else {

			mainTimelineObj = new ArrayList<>();
			contract = rigPlanBo.findAllContract();

			for (Contract c : contract) {
				mainMapObj = new HashMap<>();
				timelineMapObj = new ArrayList<>();
				getTimelineDetailsPdf(rigTyp, wellTyp, block, status, c.getContract_status());
				mainMapObj.put("contract", c.getContract_status());
				mainMapObj.put("rigLine", getTimelineMapObj());
				mainTimelineObj.add(mainMapObj);
			}
		}

		PdfPCell RigYearBeforeCell = new PdfPCell(tableRigYearBefore);

		return RigYearBeforeCell;
	}

	public void initPdf() {
		try {

			mainTimelineObj = new ArrayList<>();
			rig = rigPlanBo.findAllRig();
			contract = rigPlanBo.findAllContract();

			for (Contract c : contract) {
				System.out.println(c.getContract_status());
				mainMapObj = new HashMap<>();
				timelineMapObj = new ArrayList<>();
				for (Rig r : rig) {
					getTimelineDetailsPdf(r.getRig_typ(), this.getWellType(), this.getBlock(), this.getStatus(),
							c.getContract_status());
					mainMapObj.put("contract", c.getContract_status());
					mainMapObj.put("rigLine", getTimelineMapObj());
				}
				mainTimelineObj.add(mainMapObj);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getTimelineDetailsPdf(String rig, String well, String block, String status, String contract_stat) {
		try {

			HashMap<String, Object> modelMapPrinter = new HashMap<String, Object>();
			Date now = new Date();
			List<RigPlan> rpObjList = rigPlanBo.findByRigCriteria(rig, well, block, status, contract_stat);
			String startS = null;
			String endS = null;
			@SuppressWarnings("unused")
			String style = null;
			printerList = new ArrayList<Printer>();

			for (RigPlan rpObj : rpObjList) {
				now = new Date();
				@SuppressWarnings("unused")
				Date end = new Date(now.getTime() - 12 * 60 * 60 * 1000 * 7);
				@SuppressWarnings("unused")
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
						printer = new Printer(rpObj.getRp_id(), rig, rpObj.getRig_name(), rpObj.getWell_name(), well,
								status, block, rpObj.getRig_up(), rpObj.getRig_down(), rpObj.getStart_date(),
								rpObj.getEnd_date(), contract_stat);

						printerList.add(printer);
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
					printer = new Printer(rpObj.getRp_id(), rig, rpObj.getRig_name(), rpObj.getWell_name(), well,
							status, block, rpObj.getRig_up(), rpObj.getRig_down(), rpObj.getStart_date(),
							rpObj.getEnd_date(), contract_stat);

					printerList.add(printer);

					if (rpObj.getRig_down() != null) {
						startS = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rpObj.getEnd_date());
						endS = new SimpleDateFormat("dd/MM/yyyy").format(rpObj.getRig_down());

						start = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(startS);
						end = new SimpleDateFormat("dd/MM/yyyy").parse(endS);

						// long r = Math.round(Math.random() * 2);
						String rigDown = "Rig Down";

						// create an event with content, start / end dates, editable flag, group name
						// and custom style class
						printer = new Printer(rpObj.getRp_id(), rig, rpObj.getRig_name(), rpObj.getWell_name(), well,
								status, block, rpObj.getRig_up(), rpObj.getRig_down(), rpObj.getStart_date(),
								rpObj.getEnd_date(), contract_stat);

						printerList.add(printer);
					}
				}
				modelMapPrinter.put("rigtype", rig);
				modelMapPrinter.put("model", printerList);
			}
			if (rpObjList != null && rpObjList.size() > 0) {
				timelineMapObj.add(modelMapPrinter);
				System.out.println("timeline Obj" + timelineMapObj);
				calcDays(timelineMapObj);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void calcDays(List<HashMap<String, Object>> TimelineMapObjPrinter) {
		int i = 1;
		Iterator itrModel = null;
		String key = null;
		String rigtype = null;
		Object values = null;

		for (HashMap<String, Object> printerMap : TimelineMapObjPrinter) {
			for (Map.Entry<String, Object> entry : printerMap.entrySet()) {
				key = entry.getKey();
				values = entry.getValue().toString();

				System.out.println(" Key : " + entry.getKey());
				System.out.println(" Value :" + entry.getValue());

				if (entry.getKey().equalsIgnoreCase("rigtype")) {
						rigtype = entry.getKey();
				}

				if (entry.getKey().equalsIgnoreCase("model")) {
					while (itrModel.hasNext()) {
						Printer prt = (Printer) itrModel.next();
						System.out.println("Print : " + prt.getRp_id() + prt.getRig_typ() + prt.getRig_name() + prt.getWell_name() + prt.getWell_name() +
								prt.getStatus() + prt.getBlock() + prt.getRig_up() + prt.getRig_down() + prt.getStart_date() +
								prt.getEnd_date() + prt.getContract_stat());

						System.out.println("Count :" + i++);
					}
				}

			}

		}
	}
	
	public List<HashMap<String, Object>> getTimelineMapObj() {
		return timelineMapObj;
	}

	public void setTimelineMapObj(List<HashMap<String, Object>> timelineMapObj) {
		this.timelineMapObj = timelineMapObj;
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
}
