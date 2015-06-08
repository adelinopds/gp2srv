package x.mvmn.gp2srv.service.gphoto2.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import x.mvmn.gp2srv.service.gphoto2.ConfigParser;
import x.mvmn.log.api.Logger;

public class GP2CmdCaptureImage extends AbstractGPhoto2Command {

	protected static final String[] COMMAND_STR_KEEP = new String[] { "--capture-image", "--keep" };
	protected static final String[] COMMAND_STR_NO_KEEP = new String[] { "--capture-image", "--no-keep" };

	protected static final Pattern RESULT_PATTERN = Pattern.compile("^New file is in location (.+) on the camera$");

	protected final Boolean keepImageOnCamera;
	protected volatile String resultFile;

	public GP2CmdCaptureImage(final Boolean keepImageOnCamera, final Logger logger) {
		super(logger);
		this.keepImageOnCamera = keepImageOnCamera;
	}

	public String[] getCommandString() {
		return keepImageOnCamera == null ? null : (keepImageOnCamera ? COMMAND_STR_KEEP : COMMAND_STR_NO_KEEP);
	}

	public void submitRawStandardOutput(final String standardOutput) {
		super.submitRawStandardOutput(standardOutput);
		final Matcher matcher = RESULT_PATTERN.matcher(standardOutput.split(ConfigParser.LINE_SEPARATOR)[0]);
		if (matcher.find()) {
			resultFile = matcher.group(1);
		}
	}

	public String getResultFile() {
		return resultFile;
	}
}
