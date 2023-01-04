package sg.edu.nus.iss.app.workshop12.controller;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.iss.app.workshop12.exception.RandNumException;
import sg.edu.nus.iss.app.workshop12.models.Generate;

@Controller
@RequestMapping(path = "/")
public class RNGController {

    // redirect
    @GetMapping
    public ModelAndView redirect(ModelMap model) {
        // model.addAttribute("attribute", "redirectWithRedirectPrefix");
        return new ModelAndView("redirect:/rand/show", model);
    };

    @GetMapping(path = "rand/show")
    public String showRandForm(Model model) {
        // instantiate the generate object
        Generate g = new Generate();
        // bind the g.numOfNums to the text field
        // associate the bind var to the generate.html page
        model.addAttribute("generatedObj", g);
        return "generate";
    };

    // the post mapping endpoint is the same as the data-th-action="@{/rand/result}
    // endpoint in the generate.html
    // it receives the generatedObj object that was populated by the form
    @PostMapping(path = "rand/result")
    public String postRandNum(@ModelAttribute Generate generated, Model model) {
        model.addAttribute("randNumArray", this.getRandNums(generated.getnumOfNums()));
        model.addAttribute("numOfNums", generated.getnumOfNums());
        return "result";
    }

    private Integer[] getRandNums(Integer numOfNums) {
        // only accepts num in range 0 to 30
        // throws error page if false
        if (numOfNums < 0 || numOfNums > 30) {
            throw new RandNumException();
        }

        Integer maxNumsCanBeGenerated = 30;
        Random rand = new Random();
        Set<Integer> listOfRandNums = new HashSet<Integer>();
        Integer[] array = new Integer[numOfNums];
        while (listOfRandNums.size() < numOfNums) {
            Integer num = rand.nextInt(maxNumsCanBeGenerated);
            listOfRandNums.add(num);
        }
        // System.out.println(listOfRandNums);
        return listOfRandNums.toArray(array);
    }
}
