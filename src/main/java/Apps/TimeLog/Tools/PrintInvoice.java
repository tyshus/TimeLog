package Apps.TimeLog.Tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import Apps.TimeLog.Models.Company;
import Apps.TimeLog.Models.Invoice;
import Apps.TimeLog.Models.Model;

public class PrintInvoice {
	Model model = Model.getModel();
	private String pth, tmp_doc, pdf;

	public PrintInvoice(Invoice invoice) throws Exception {
		HWPFDocument doc = openDocument(
				model.prop.getProperty("template_path") + model.prop.getProperty("invoice_template"));
		pth = invoice.getCompany() + "/Invoices";
		tmp_doc = pth + "/" + invoice.getSerno() + ".doc";
		pdf = pth + "/" + invoice.getSerno() + ".pdf";
		model.createDirectory(pth);

		if (doc != null) {
			DecimalFormat df = new DecimalFormat("0.00");
			df.setRoundingMode(RoundingMode.UP);
			Company company = model.getCompany(invoice.getCompany());
			doc = replaceText(doc, "var_invno", invoice.getSerno());
			doc = replaceText(doc, "var_invdate", invoice.getDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
			doc = replaceText(doc, "var_company", company.getName());
			doc = replaceText(doc, "var_address", company.getAddress());
			doc = replaceText(doc, "var_code", company.getCode());
			doc = replaceText(doc, "var_vatno", company.getVatRegNo());
			doc = replaceText(doc, "var_operation", invoice.getOperation());
			doc = replaceText(doc, "var_period", invoice.getPeriod());
			doc = replaceText(doc, "var_sum", df.format(invoice.getAmount()));
			doc = replaceText(doc, "var_totsum", df.format(invoice.getAmount()));
			saveDocument(doc, tmp_doc);
			new PrintPDF(tmp_doc, pdf);
			new File(tmp_doc).delete();
			if (new File(pdf).exists()) {
				invoice.setPrinted(true);
				invoice.setPrintout(pdf);
				model.merge(invoice);
				System.out.println(invoice.getPrintout());
				model.msg("Invoice printed!");
			} else
				model.msgW("Failed to print invoice!");
		}
	}

	private HWPFDocument replaceText(HWPFDocument doc, String findText, String replaceText) {
		Range r = doc.getRange();
		for (int i = 0; i < r.numSections(); ++i) {
			Section s = r.getSection(i);
			for (int j = 0; j < s.numParagraphs(); j++) {
				Paragraph p = s.getParagraph(j);
				for (int k = 0; k < p.numCharacterRuns(); k++) {
					CharacterRun run = p.getCharacterRun(k);
					String text = run.text();
					if (text.contains(findText)) {
						run.replaceText(findText, replaceText);
					}
				}
			}
		}
		return doc;
	}

	private HWPFDocument openDocument(String filename) throws Exception {
		HWPFDocument document = null;
		if (new File(filename).exists()) {
			document = new HWPFDocument(new POIFSFileSystem(new File(filename)));
		} else
			model.msgW(".doc template not defined or not found!");
		return document;
	}

	private void saveDocument(HWPFDocument doc, String file) {
		try (FileOutputStream out = new FileOutputStream(file)) {
			doc.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
