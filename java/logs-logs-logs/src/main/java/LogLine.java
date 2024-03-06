import javax.sound.midi.MidiDevice.Info;

public class LogLine {
    private String logLine;

    public LogLine(String logLine) {
        this.logLine = logLine;
    }

    public LogLevel getLogLevel() {
        String level = this.logLine.substring(1,4);
        switch (level) {
            case "TRC":
                return LogLevel.TRACE;
            case "DBG":
                return LogLevel.DEBUG;
            case "INF":
                return LogLevel.INFO;
            case "WRN":
                return LogLevel.WARNING;
            case "ERR":
                return LogLevel.ERROR;
            case "FTL":
                return LogLevel.FATAL;
            default:
                return LogLevel.UNKNOWN;
        }
    }

    public String getOutputForShortLog() {
        return String.format(
            "%d:%s", 
            this.getLogLevel().getLevelInfo(), 
            this.logLine.substring(7)
            );
    }
}
