package com.example.diningroommanager.controllers;

import com.example.diningroommanager.entities.Menu;
import com.example.diningroommanager.repositories.MenuItemRepository;
import com.example.diningroommanager.repositories.MenuRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MenuController {
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    public MenuController(MenuRepository menuRepository, MenuItemRepository menuItemRepository) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @GetMapping("/menu")
    public String menu(Model model){
        // Get all the menu
        var menus = menuRepository.findAll();

        model.addAttribute("menus", menus);

        return "menu/index";
    }

    @GetMapping("menu/create")
    private String create(Model model){
        var menu = new Menu();

        model.addAttribute("menu", menu);

        return "menu/create";
    }

    @PostMapping("menu/create")
    public String create(@Valid Menu menu, BindingResult br, Model model){
        if (!br.hasErrors()){
            menuRepository.save(menu);

            return "redirect:/menu";
        }else {
            return "menu/create";
        }
    }

    @GetMapping("menu/edit/{id}")
    public String edit(Model model, @PathVariable int id){
        var menu = menuRepository.findById(id);

        if (menu.isPresent()){
            model.addAttribute("menu", menu.get());
        }

        return "menu/edit";
    }

    @PostMapping("menu/edit/{id}")
    public String edit(@Valid Menu menu, BindingResult br, Model model){
        if (!br.hasErrors()){
            menuRepository.save(menu);

            return "redirect:/menu";
        }{
            return "menu/edit";
        }
    }

    @GetMapping(value = "/menu/detail/{id}")
    public String details(@PathVariable int id, Model model) {
        var menu = menuRepository.findById(id);

        var menuItems = menuItemRepository.findByMenuId(id);
        model.addAttribute("menuItems", menuItems);

        if (menu.isPresent()) {
            model.addAttribute("menu", menu.get());
        }

        return "menu/detail";
    }

    @GetMapping(value = "/menu/delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        var menu = menuRepository.findById(id);

        if (menu.isPresent()) {
            model.addAttribute("menu", menu.get());
        }

        return "menu/delete";
    }

    @PostMapping(value = "/menu/delete/{id}")
    public String deleteConfirm(@PathVariable int id) {

        menuRepository.deleteById(id);

        return "redirect:/menu";
    }
}
