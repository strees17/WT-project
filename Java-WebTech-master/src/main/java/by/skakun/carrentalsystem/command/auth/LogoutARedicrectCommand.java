package by.skakun.carrentalsystem.command.auth;

import by.skakun.carrentalsystem.command.ActionCommand;
import by.skakun.carrentalsystem.util.ConfigurationManager;
import by.skakun.carrentalsystem.util.StatisticsTagHandler;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 *
 * @author Skakun
 *
 * processing redirect to logouta.jsp
 */
public class LogoutARedicrectCommand implements ActionCommand {

    private static final Logger LOG = Logger.getLogger(LogoutARedicrectCommand.class);

    /**
     *
     * @param request
     * @return logouta.jsp page (logout page for admin with statistics of
     * unprocessed orders
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        int size = StatisticsTagHandler.getOrderStats().size();
        if (size > 0) {
            request.setAttribute("flag", "1");
            request.setAttribute("rw", size);
        }

        LOG.debug("->logouta.jsp");
        page = ConfigurationManager.getProperty("path.page.logouta");
        return page;

    }

}
