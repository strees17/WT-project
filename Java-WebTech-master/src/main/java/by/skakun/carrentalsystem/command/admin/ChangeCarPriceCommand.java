package by.skakun.carrentalsystem.command.admin;

import by.skakun.carrentalsystem.command.ActionCommand;
import by.skakun.carrentalsystem.connectionpool.ConnectionPool;
import by.skakun.carrentalsystem.dao.CarDao;
import by.skakun.carrentalsystem.dao.impl.CarDaoImpl;
import by.skakun.carrentalsystem.exception.DAOException;
import by.skakun.carrentalsystem.util.ConfigurationManager;
import by.skakun.carrentalsystem.util.EnteredInfoValidator;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 *
 * @author Skakun changes the price of car and returns him to the car info page
 */
public class ChangeCarPriceCommand implements ActionCommand {

    private static final Logger LOG = Logger.getLogger(ChangeCarPriceCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        boolean flag;
        String carid = (String) request.getParameter("carid");
        int id = Integer.parseInt(carid);
        String carprice = (String) request.getParameter("newprice");
        int price = Integer.parseInt(carprice);
        if (!EnteredInfoValidator.rentPrice(price)) {
            page = ConfigurationManager.getProperty("path.page.error");
            return page;
        }
        CarDao carDao;
        try {
            carDao = new CarDaoImpl();
        } catch (DAOException ex) {
            LOG.fatal("Couldn't establish the connection to the database");
            LOG.debug("->errorpage");
            page = ConfigurationManager.getProperty("path.page.error");
            return page;
        }
        try {
            flag = carDao.changeCarprice(price, id);
            if (flag) {
                request.setAttribute("psuccess", "1");
                request.getSession().setAttribute("carprice", price);
            } else {
                request.setAttribute("pfail", "1");
            }
        } catch (DAOException ex) {
            LOG.error("DAOException while ChangeCarPriceCommand");
        }
        page = ConfigurationManager.getProperty("path.page.carchange");
        return page;
    }

}
