package alex.webapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * A generic REST controller that have 4 main methods like CRUD fro HTTP request
 * GET
 * POST
 * PUT
 * DELETE
 * and one URI binding
 */
@RestController
@RequestMapping("templates") // path for url: http://localhost:8080/templates
public class TemplateController {

    private final static Logger logger = LoggerFactory.getLogger(TemplateController.class);

    @GetMapping // bind to GET HTTP operation
    public String getTemplate() {
        return "get template was called.";
    }

    @PostMapping // bind to POST HTTP operation
    public String createTemplate() {
        return "create template was called.";
    }

    @PutMapping // bind to PUT HTTP operation
    public String updateTemplate() {
        return "update template was called.";
    }

    @DeleteMapping // bind to DELETE HTTP operation
    public String deleteTemplate() {
        return "delete template was called.";
    }

}
