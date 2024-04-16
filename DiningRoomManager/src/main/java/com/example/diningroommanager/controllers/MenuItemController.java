package com.example.diningroommanager.controllers;

import com.example.diningroommanager.entities.MenuItem;
import com.example.diningroommanager.login.LoginToken;
import com.example.diningroommanager.repositories.MenuItemRepository;
import com.example.diningroommanager.repositories.MenuRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MenuItemController {
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;
    private final LoginToken loginToken;


    public MenuItemController(MenuRepository menuRepository, MenuItemRepository menuItemRepository, LoginToken loginToken) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;
        this.loginToken = loginToken;
    }

    @ModelAttribute("authenticated")
    public boolean getAuthenticated(){
        return loginToken.hasToken();
    }


    @GetMapping(value = "menuItem/create/{id}")
    public String create(Model model, @PathVariable int id) {
        var menu = menuRepository.findById(id).orElse(null);

        if (menu != null){
            var menuItem = new MenuItem();

            // Set the menu of the new MenuItems object
            menuItem.setMenu(menu);
            model.addAttribute("menuItem", menuItem);
        }

        return "menuItem/create";
    }

    @PostMapping(value = "menuItem/create/{id}")
    public String create(@PathVariable int id, @Valid MenuItem menuItem, BindingResult br, Model model) {
        var menu = menuRepository.findById(id).orElse(null);

        if (!br.hasErrors()) {
            // Create new Menu Item
            var newMenuItem = new MenuItem(menuItem.getName(), menuItem.getDescription(), menu);

            menuItemRepository.save(newMenuItem);

            return "redirect:/menu/detail/" + id;
        } else {
            menuItem.setMenu(menu);
            model.addAttribute("menuItem", menuItem);

            return "menuItem/create";
        }
    }


    @GetMapping(value = "menuItem/edit/{id}")
    public String edit(Model model, @PathVariable int id) {
        var menuItem = menuItemRepository.findById(id);

        if (menuItem.isPresent()) {
            model.addAttribute("menuItem", menuItem.get());
        }

        return "menuItem/edit";
    }

    @PostMapping(value = "menuItem/edit/{id}")
    public String edit(@PathVariable int id, @Valid MenuItem menuItem, BindingResult br, Model model) {
        // Find the existing menu item
        var existingMenuItem = menuItemRepository.findById(id);

        // Check for validation errors
        if (!br.hasErrors()) {


            // Check if the menu item is present
            if (existingMenuItem.isPresent()){
                // Get the menu item to update
                var menuItemToUpdate = existingMenuItem.get();

                // Get the menu from the existing menu item
                var menu = menuItemToUpdate.getMenu();

                // Set and save the new details
                menuItemToUpdate.setName(menuItem.getName());
                menuItemToUpdate.setDescription(menuItem.getDescription());
                menuItemToUpdate.setMenu(menu);

                menuItemRepository.save(menuItemToUpdate);

                // Redirect back to the detail page
                return "redirect:/menu/detail/" + menu.getId();
            }
        }

        // set the menu of the submitted menuItem
        if (existingMenuItem.isPresent()){
            menuItem.setMenu(existingMenuItem.get().getMenu());
        }

        model.addAttribute("menuItem", menuItem);

        return "menuItem/edit";
    }


    @GetMapping(value = "menuItem/delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        // Find the menu item by id
        var menuItem = menuItemRepository.findById(id);

        // add the model If the menu item exists
        if (menuItem.isPresent()) {
            model.addAttribute("menuItem", menuItem.get());
        }

        // Return the delete confirmation page
        return "menuItem/delete";
    }

    @PostMapping(value = "menuItem/delete/{id}")
    public String deleteConfirm(@PathVariable int id) {
        // Find the existing menu item
        var existingMenuItem = menuItemRepository.findById(id);

        // Check if the menu item exists
        if (existingMenuItem.isPresent()){

            // Get the menu item to delete
            var menuItem = existingMenuItem.get();

            // Get the menu associated with the menu item
            var menu = menuItem.getMenu();

            // Delete the menu item by id
            menuItemRepository.deleteById(id);

            // Redirect back to the menu detail page
            return "redirect:/menu/detail/" + menu.getId();
        }

        return "menuItem/delete";
    }

}
