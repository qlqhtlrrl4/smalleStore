package kr.ac.hansung.cse.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.hansung.cse.model.Product;
import kr.ac.hansung.cse.service.ProductService;

@Controller
@RequestMapping("/admin") //class level
public class AdminController {
	
	
	@Autowired
	private ProductService productService;
	
	
	@RequestMapping // method level
	public String adminPage() {
		
		return "admin";
	}
	
	@RequestMapping("/productInventory") // class level에 admin이 있어서 /admin/productInventory 라고 안해도됌
	public String getProducts(Model model) { //controller -> model
		
		List<Product> products = productService.getAllProduct();
		model.addAttribute("products", products);
		return "productInventory";
	}
	
	@RequestMapping(value="/productInventory/addProduct", method=RequestMethod.GET)
	public String addProduct(Model model) {
		
		Product product = new Product();
		product.setCategory("컴퓨터");
		
		model.addAttribute("product",product);
		
		return "addProduct";
	}
	
	@RequestMapping(value="/productInventory/addProduct", method=RequestMethod.POST)
	public String addProductPost(@Valid Product product, BindingResult result) {
		
		if(result.hasErrors()) {	  
		  return "addProduct";
		}
		
		productService.addProduct(product);
		
		//재고현황을 다시 보기위해서 바로 productInventory 때리면 products 값이 안넘어가서 아무것도안뜸
		return "redirect:/admin/productInventory";
	}
	
	@RequestMapping(value="/productInventory/deleteProduct/{id}", method=RequestMethod.GET)
	public String deleteProduct(@PathVariable int id) {
		
		Product product = productService.getProduct(id);
		
		productService.deleteProduct(product);
		
		return "redirect:/admin/productInventory";
	}
	
	@RequestMapping(value="/productInventory/updateProduct/{id}", method=RequestMethod.GET)
	public String updateProduct(@PathVariable int id,Model model) {
		
		Product product = productService.getProduct(id);
		
		model.addAttribute("product", product);
		
		return "updateProduct";
	}
	
	@RequestMapping(value="/productInventory/updateProduct", method=RequestMethod.POST)
	public String updateProduct(Product product, BindingResult result) {
		
		if(result.hasErrors()) {					
			return "updateProduct";			
		}
		
		productService.updateProduct(product);
		
		return "redirect:/admin/productInventory";
	}

}
