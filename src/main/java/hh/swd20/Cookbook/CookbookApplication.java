package hh.swd20.Cookbook;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hh.swd20.Cookbook.domain.Category;
import hh.swd20.Cookbook.domain.CategoryRepository;
import hh.swd20.Cookbook.domain.Unit;
import hh.swd20.Cookbook.domain.UnitRepository;

@SpringBootApplication
public class CookbookApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CookbookApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner cookbookDemo(CategoryRepository crepository, UnitRepository unrepository) {
		return (args) -> {
			
			Unit unit = new Unit("");
			Unit unit1 = new Unit("tl");
			Unit unit2 = new Unit("rkl");
			Unit unit3 = new Unit("mm");
			Unit unit4 = new Unit("dl");
			Unit unit5 = new Unit("l");
			Unit unit6 = new Unit("g");
			Unit unit7 = new Unit("kg");
			Unit unit8 = new Unit("prk");
			Unit unit9 = new Unit("pkt");
			Unit unit10 = new Unit("rs");
			Unit unit11 = new Unit("tlk");
			List<Unit> units = List.of(unit, unit1, unit2, unit3, unit4, unit5, unit6, unit7, unit8, unit9, unit10, unit11);
			unrepository.saveAll(units);
	
			Category category = new Category("");
			Category category1 = new Category("Seka");
			Category category2 = new Category("Vegaani");
			Category category3 = new Category("Kasvis");
			List<Category> categorys = List.of(category, category1, category2, category3);
			crepository.saveAll(categorys);

		};
	}
	
	

}
