package academy.digitallab.store.product;

import academy.digitallab.store.product.entity.Category;
import academy.digitallab.store.product.entity.Product;
import academy.digitallab.store.product.repository.ProductRepository;
import academy.digitallab.store.product.service.ProductService;
import academy.digitallab.store.product.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class ProductServiceMockTest {
    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    private Date date;

    @BeforeEach
    public void setup() {
        date = new Date();
        productService = new ProductServiceImpl(productRepository);
        Product computer = Product.builder()
                .name("computer")
                .category(Category.builder().id(1L).build())
                .description("")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("1240.99"))
                .status("Created")
                .createAt(date).build();

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(computer));
        Mockito.when(productRepository.save(computer)).thenReturn(computer);
    }

    @Test
    public void whenValidGetProductThenReturnProduct() {
        Product product = productService.getProduct(1L);
        Assertions.assertThat(product.getName()).isEqualTo("computer");
        Assertions.assertThat(product.getCategory()).isEqualTo(Category.builder().id(1L).build());
        Assertions.assertThat(product.getDescription()).isEqualTo("");
        Assertions.assertThat(product.getStock()).isEqualTo(Double.parseDouble("10"));
        Assertions.assertThat(product.getPrice()).isEqualTo(Double.parseDouble("1240.99"));
        Assertions.assertThat(product.getStatus()).isEqualTo("Created");
        Assertions.assertThat(product.getCreateAt()).isEqualTo(date);
    }

    @Test
    public void whenValidUpdateStock_ThenReturnNewStock() {
        Product newStock = productService.updateStock(1L, Double.parseDouble("8"));
        Assertions.assertThat(newStock.getStock()).isEqualTo(Double.parseDouble("18"));
    }
}
