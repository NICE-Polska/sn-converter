package pl.nice.snconverter.utils.report;

import lombok.Data;
import pl.nice.snconverter.utils.report.message.ReportMessageContent;

import java.util.List;

@Data
public abstract class Report {
    protected ReportType reportType;
    protected byte[] reportBytes;
    protected String reportName;
    protected String fileName;
    protected String fileNameExtension;
    protected String contentType;
    protected List<String> headersNames;
    protected List<String> data;

    public void createReport() {
    }
}
