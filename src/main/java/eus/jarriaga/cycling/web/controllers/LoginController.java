package eus.jarriaga.cycling.web.controllers;

import eus.jarriaga.cycling.business.repositories.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserRepository userRepository;

   /* @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RedirectView submit(@RequestParam("username") String username, @RequestParam("password") String password,
                               HttpServletRequest request, Principal principal) {

        logger.info("Usuario " + username + " con contraseña " + password);
        logger.info(request.getSession().getId());
        User user = userRepository.findUserByEmail(username);

        request.getSession().setAttribute("user", user);
        RedirectView redirect = new RedirectView("home.html");
        return redirect;
    }
*/
}
