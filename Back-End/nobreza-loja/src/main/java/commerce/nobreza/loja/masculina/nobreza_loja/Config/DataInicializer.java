package commerce.nobreza.loja.masculina.nobreza_loja.Config;
import commerce.nobreza.loja.masculina.nobreza_loja.Entity.Category;
import commerce.nobreza.loja.masculina.nobreza_loja.Entity.Role;
import commerce.nobreza.loja.masculina.nobreza_loja.Repository.CategoryRepository;
import commerce.nobreza.loja.masculina.nobreza_loja.Repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class DataInicializer implements CommandLineRunner {

 private final RoleRepository roleRepository;
 private final CategoryRepository categoryRepository;

    //Inicializar como padrão ROLE_USER E ROLER_ADMIN no banco
    @Override
    public void run(String... args) {
        if (!roleRepository.existsByName("ROLE_USER")) {
            roleRepository.save(new Role("ROLE_USER"));
        }
        if (!roleRepository.existsByName("ROLE_ADMIN")) {
            roleRepository.save(new Role("ROLE_ADMIN"));
        }

        List<String> defaultCategories = Arrays.asList(
                "Ternos", "Camisas", "Sapatos", "Acessórios", "Calças", "Gravatas"
        );

        for (String categoryName : defaultCategories) {
            if (categoryRepository.findByName(categoryName).isEmpty()) {
                categoryRepository.save(new Category(categoryName));
            }
        }
    }
}
