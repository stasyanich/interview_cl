package ru.stasyan.interview_cl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.stasyan.interview_cl.entity.EntryComment;
import ru.stasyan.interview_cl.entity.Resources;
import ru.stasyan.interview_cl.entity.StringArray;
import ru.stasyan.interview_cl.entity.StringElement;
import ru.stasyan.interview_cl.service.DataService;


import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class IndexController {

    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private DataService dataService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getAndShowResources() {
        LOG.info(dataService==null?"dataService is empty":"dataService is filled");
        Resources resources =  dataService.read();
        return  new ModelAndView("full","resources", resources);
    }

    @PostMapping(value = "/")
    public String saveChanges(@ModelAttribute("resources") Resources resources){
        LOG.info(resources.toString());

        convertResources(resources);
         resources = testRecordResource(); //fixme удалить

        dataService.save(resources);

        return "redirect:/";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
          binder.getTarget();
        }


    @SuppressWarnings("unchecked")
    private Resources testRecordResource(){
        Resources resources = new Resources();
        ArrayList list = resources.getStringOrStringArray();
        list.add(new StringElement("Preferences", "fdgdfg"));
        list.add(new StringElement("FeedBack", "Feeddfgdfback"));
        list.add(new StringElement("ShareLink", "Share dgdgdfg app"));
        list.add(new StringElement("EnterPromoCode", "Enter promo code"));
        list.add(new StringElement("AboutApplication", "About"));
        list.add(new StringElement("OrderTab", "ORDER"));
        list.add(new StringElement("SelectTariff", "SELECT A RATE"));
        list.add(new StringElement("ExtraOptions", "Requests for your trip"));
        list.add(new StringElement("prompt_phone", "Phone"));
        list.add(new StringElement("prompt_password", "Code"));
        list.add(new StringElement("january_in_date", "Jan"));
        list.add(new StringElement("february_in_date", "Feb"));
        list.add(new StringElement("march_in_date", "March"));
        list.add(new StringElement("april_in_date", "April"));
        list.add(new StringArray("months_short",new ArrayList<>(Arrays.asList("item1","item2","item3","item4","item5"))));
        //list.add(new EntryComment("<-- comment!!!-->"));

        resources.setStringOrStringArray(list);
        return resources;
    }

    @SuppressWarnings("unchecked")
    private Resources convertResources(Resources resources) {

        ArrayList convertedElements = new ArrayList();
        ArrayList elementsFromResources = resources.getStringOrStringArray();
        for (Object element : elementsFromResources) {
            if (element instanceof StringElement) {
                convertedElements.add(element);
            }
            if (element instanceof StringArray) {
                convertedElements.add(element);
            }
        }
        resources.setStringOrStringArray(convertedElements);


        return resources;
    }

}
