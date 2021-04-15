package pl.nice.snconverter.utils.report;

import pl.nice.snconverter.utils.report.message.ReportMessageContent;

import java.io.BufferedReader;
import java.util.List;

public class ReportFactory extends Report {

    private ReportFactory() {
        throw new IllegalStateException(this.getClass().getName());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Report create(ReportType reportType) {
        if (reportType == ReportType.WORD) {
            WordReport wordReport =  new WordReport();
            wordReport.reportType = reportType;
            return wordReport;
        } else if (reportType == ReportType.PDF) {
            PDFReport pdfReport = new PDFReport();
            pdfReport.reportType = reportType;
            return pdfReport;
        } else if (reportType == ReportType.EXCEL) {
            ExcelReport excelReport = new ExcelReport();
            excelReport.reportType = reportType;
            return excelReport;
        } else if (reportType == ReportType.PLAINTEXT){
            PlainTextReport plainTextReport = new PlainTextReport();
            plainTextReport.reportType = reportType;
            return plainTextReport;
        }
        return null; //TODO spróbować obsłuzyć to wyjatkiem
    }

    public static final class Builder {
        private ReportType reportType;
        private byte[] reportBytes;
        private String reportName;
        private String fileName;
        private String fileNameExtension;
        private String contentType;
        private List<String> headersNames;
        private List<String> data;

        public Report build() {
            if (reportType == null) {
                throw new IllegalStateException("Report Type cannot be null");
            } else if (reportType == ReportType.WORD) {
                WordReport wordReport = new WordReport();
                wordReport.reportType = this.reportType;
                wordReport.fileName = this.fileName;
                wordReport.reportBytes = this.reportBytes;
                wordReport.contentType = this.contentType;
                wordReport.fileNameExtension = this.fileNameExtension;
                wordReport.reportName = this.reportName;
                wordReport.headersNames = this.headersNames;
                wordReport.data = this.data;
                return wordReport;
            } else if (reportType == ReportType.EXCEL) {
                ExcelReport excelReport = new ExcelReport();
                excelReport.reportType = this.reportType;
                excelReport.fileName = this.fileName;
                excelReport.reportBytes = this.reportBytes;
                excelReport.contentType = this.contentType;
                excelReport.fileNameExtension = this.fileNameExtension;
                excelReport.reportName = this.reportName;
                excelReport.headersNames = this.headersNames;
                excelReport.data = this.data;
                return excelReport;
            } else if (reportType == ReportType.PDF) {
                PDFReport pdfReport = new PDFReport();
                pdfReport.reportType = this.reportType;
                pdfReport.fileName = this.fileName;
                pdfReport.reportBytes = this.reportBytes;
                pdfReport.contentType = this.contentType;
                pdfReport.fileNameExtension = this.fileNameExtension;
                pdfReport.reportName = this.reportName;
                pdfReport.headersNames = this.headersNames;
                pdfReport.data = this.data;
                return pdfReport;
            } else {
                PlainTextReport plainTextReport = new PlainTextReport();
                plainTextReport.reportType = this.reportType;
                plainTextReport.fileName = this.fileName;
                plainTextReport.reportBytes = this.reportBytes;
                plainTextReport.contentType = this.contentType;
                plainTextReport.fileNameExtension = this.fileNameExtension;
                plainTextReport.reportName = this.reportName;
                plainTextReport.headersNames = this.headersNames;
                plainTextReport.data = this.data;
                return plainTextReport;
            }
        }

        public Builder reportType(ReportType reportType) {
            this.reportType = reportType;
            return this;
        }

        public Builder reportBytes(byte[] reportBytes) {
            this.reportBytes = reportBytes;
            return this;
        }

        public Builder reportName(String reportName) {
            this.reportName = reportName;
            return this;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder fileNameExtension(String fileNameExtension) {
            this.fileNameExtension = fileNameExtension;
            return this;
        }

        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder headers(List<String> headersNames) {
            this.headersNames = headersNames;
            return this;
        }

        public Builder data(List<String> data) {
            this.data = data;
            return this;
        }

        private <T> T test(T t) {
            return t;
        }
    }
}
