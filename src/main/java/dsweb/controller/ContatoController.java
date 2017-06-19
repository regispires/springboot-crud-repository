package dsweb.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dsweb.model.Contato;
import dsweb.repository.ContatoRepository;

@Controller
public class ContatoController {

	@Autowired
	private ContatoRepository contatoRepo;
	
	@GetMapping("/")
	public String home() {
		return "forward:/contatos";
	}
	
	@GetMapping("/contatos")
	public String listaContatos(Model model) {
		Iterable<Contato> lista = contatoRepo.findAll( 
				new Sort(new Sort.Order(Sort.Direction.ASC, "nome")));
		
		model.addAttribute("contatos", lista);
		return "lista_contatos";
	}
	
	@GetMapping("/contatos/add")
	public String insereForm(Model model) {
		model.addAttribute("contato", new Contato());
		return "insere_contato";
	}
	
	@RequestMapping("/contatos/{id}/update")
	public String alteraForm(@PathVariable Integer id, Model model) {
		System.out.println("id: " + id);
		Contato c = contatoRepo.findOne(id);
		System.out.println("contato: " + c);
		model.addAttribute("contato", c);
		return "altera_contato";
	}

	@PostMapping("/contatos")
	public String addContato(@Valid Contato contato, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()) {
			model.addAttribute("contato", contato);
			if (contato.getId() == null) {
				return "insere_contato";
			} else {
				return "altera_contato";
			}
		}
		
		if (contato.getId() == null) {
			contatoRepo.save(contato);
			redirectAttributes.addFlashAttribute("msg", "Contato inserido com sucesso.");
		} else {
			contatoRepo.save(contato);
			redirectAttributes.addFlashAttribute("msg", "Contato atualizado com sucesso.");
		}
		return "redirect:/contatos";
	}

	@GetMapping("/contatos/{id}/delete")
	public String deleteContato(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		System.out.println("id: " + id);
		Contato contato = new Contato(id);
		System.out.println("contato: " + contato);
		contatoRepo.delete(contato);
		redirectAttributes.addFlashAttribute("msg", "Contato removido com sucesso.");
		return "redirect:/contatos";
	}
	
}
