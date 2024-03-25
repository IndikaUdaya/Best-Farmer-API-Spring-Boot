package com.indikaudaya.bestfarmer.controllers;

import com.indikaudaya.bestfarmer.dto.*;
import com.indikaudaya.bestfarmer.entities.*;
import com.indikaudaya.bestfarmer.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductServices productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }


    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/seller/{id}")
    public List<ProductDTO> getProductBySellerId(@PathVariable Long id) {
        List<Product> bySellerId = productService.getProductBySellerId(id);

        List<ProductDTO> pd = new ArrayList<>();

        if (bySellerId != null) {
            for (Product p : bySellerId) {

                ProductDTO productDTO = new ProductDTO();

                ArrayList<ProductImageDTO> pi = new ArrayList<>();
                p.getProductImages().forEach(productImage -> {
                    pi.add(new ProductImageDTO(productImage.getPath()));
                });

                productDTO.setProductImages(pi);
                productDTO.setId(p.getId());
                productDTO.setCategory(new CategoryDTO(p.getCategory().getId(), p.getCategory().getName()));
                productDTO.setName(p.getName());
                productDTO.setDescription(p.getDescription());
                productDTO.setQty(p.getQty());
                productDTO.setPrice(p.getPrice());
                productDTO.setType(p.getType());
                productDTO.setUnit(p.getUnit());
                productDTO.setDeliveryAvailable(p.isDeliveryAvailable());
                productDTO.setCartCount(p.getCarts().size());
                productDTO.setWatchlistCount(p.getWatchlistList().size());

                ArrayList<SellerReviewDTO> sellerReviewDTOS = new ArrayList<>();

                for (SellerReview sellerReview : p.getSeller().getBuyerReview()) {
                    sellerReviewDTOS.add(new SellerReviewDTO(
                            sellerReview.getId(),
                            sellerReview.getComment(),
                            sellerReview.getRating(),
                            new UserDTO(
                                    sellerReview.getBuyer().getId(),
                                    sellerReview.getBuyer().getEmail(),
                                    sellerReview.getBuyer().getMobile(),
                                    sellerReview.getBuyer().getUserType(),
                                    sellerReview.getBuyer().isStatus()
                            ),
                            new UserDTO(
                                    sellerReview.getSeller().getId(),
                                    sellerReview.getSeller().getEmail(),
                                    sellerReview.getSeller().getMobile(),
                                    sellerReview.getSeller().getUserType(),
                                    sellerReview.getSeller().isStatus()
                            ),
                            sellerReview.getReviewDate(),
                            sellerReview.getProduct().getId()
                    ));
                }

                UserDTO seller = new UserDTO();
                seller.setId(p.getSeller().getId());
                seller.setEmail(p.getSeller().getEmail());
                seller.setMobile(p.getSeller().getMobile());

                seller.setBuyerReview(sellerReviewDTOS);

                productDTO.setSeller(seller);

                pd.add(productDTO);
            }
        }
        return pd;
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable long id, @RequestBody Product dto) {

        dto.setId(id);
        Product p = productService.updateProduct(dto);

        ProductDTO productDTO = new ProductDTO();

        if (p != null) {

            ArrayList<ProductImageDTO> pi = new ArrayList<>();
            p.getProductImages().forEach(productImage -> {
                pi.add(new ProductImageDTO(productImage.getPath()));
            });

            productDTO.setProductImages(pi);
            productDTO.setId(p.getId());
            productDTO.setCategory(new CategoryDTO(p.getCategory().getId(), p.getCategory().getName()));
            productDTO.setName(p.getName());
            productDTO.setDescription(p.getDescription());
            productDTO.setQty(p.getQty());
            productDTO.setPrice(p.getPrice());
            productDTO.setType(p.getType());
            productDTO.setUnit(p.getUnit());
            productDTO.setDeliveryAvailable(p.isDeliveryAvailable());

            UserDTO seller = new UserDTO();
            seller.setId(p.getSeller().getId());
            seller.setEmail(p.getSeller().getEmail());
            seller.setMobile(p.getSeller().getMobile());

            productDTO.setSeller(seller);

        }
        return productDTO;
    }

    @PostMapping
    public ProductDTO saveProduct(@RequestBody ProductDTO product) {
        List<ProductImageDTO> productImages = product.getProductImages();

        Product saveProduct = new Product();

        ArrayList<ProductImage> newImage = new ArrayList<>();
        for (ProductImageDTO imageData : productImages) {
            ProductImage productImage1 = new ProductImage();
            productImage1.setPath(imageData.getPath());
            productImage1.setProduct(saveProduct);
            newImage.add(productImage1);
        }
        saveProduct.setCategory(new Category(product.getCategory().getId(), product.getCategory().getName()));
        saveProduct.setDeliveryAvailable(product.isDeliveryAvailable());
        saveProduct.setDescription(product.getDescription());
        saveProduct.setName(product.getName());
        saveProduct.setPrice(product.getPrice());
        saveProduct.setQty(product.getQty());
        saveProduct.setType(product.getType());
        saveProduct.setUnit(product.getUnit());
        saveProduct.setProductImages(newImage);
        saveProduct.setSeller(new User(product.getSeller().getId(), product.getSeller().getEmail()));

        Product saved = productService.saveProduct(saveProduct);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(saved.getId());
        return productDTO;

    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }


}
