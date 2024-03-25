package com.indikaudaya.bestfarmer.controllers;

import com.indikaudaya.bestfarmer.dto.*;
import com.indikaudaya.bestfarmer.entities.*;
import com.indikaudaya.bestfarmer.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    public OrderDTO saveOrder(@RequestBody OrderDTO orderDTO) {

        Order order = new Order();

        User user = new User();
        user.setId(orderDTO.getBuyer().getId());
        order.setBuyer(user);

        order.setDeliveryStatus(order.isDeliveryStatus());
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(orderDTO.getInvoice().getInvoiceNumber());
        invoice.setOrder(order);
        order.setInvoice(invoice);

        List<ProductOrderItem> orderItems = new ArrayList<>();

        orderDTO.getOrderItems().forEach(po -> {
            ProductOrderItem item = new ProductOrderItem();
            Product product = new Product();

            product.setId(po.getProductId());
            item.setProduct(product);
            item.setQuantity(po.getQuantity());
            item.setOrder(order);

            orderItems.add(item);
        });

        order.setOrderItems(orderItems);

        Order saveOrder = orderService.saveOrder(order);

        // response
        OrderDTO reqDto = new OrderDTO();

        ArrayList<ProductOrderItemDTO> list = new ArrayList();

        saveOrder.getOrderItems().forEach(p -> {
            ProductOrderItemDTO itemDTO = new ProductOrderItemDTO();
            itemDTO.setId(p.getId());
            itemDTO.setQuantity(p.getQuantity());

            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(p.getProduct().getId());
            itemDTO.setProduct(productDTO);

            list.add(itemDTO);
        });

        reqDto.setOrderItems(list);
        reqDto.setOrderId(saveOrder.getId());

        reqDto.setInvoice(new InvoiceDTO(saveOrder.getInvoice().getId(), saveOrder.getInvoice().getInvoiceNumber()));
        reqDto.setDeliveryStatus(saveOrder.isDeliveryStatus());

        reqDto.setOrderId(saveOrder.getId());

        return reqDto;
    }

    @PutMapping("/{oid}")
    public OrderDTO updateOrderStatus(@PathVariable long oid, @RequestBody OrderDTO orderDTO) {

        Order order = new Order();
        order.setId(oid);
        Order order1 = orderService.updateOrderStatus(order);

        OrderDTO orderDTO1 = new OrderDTO();
        orderDTO1.setOrderId(order1.getId());

        return orderDTO1;
    }

    @GetMapping
    public List<OrderDTO> getAllOrders() {
        List<Order> orderList = orderService.getAllOrders();

        List<OrderDTO> dtos = new ArrayList<>();

        orderList.forEach(order -> {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderId(order.getId());
            orderDTO.setDeliveryStatus(order.isDeliveryStatus());

            User buyer = order.getBuyer();
            UserDTO userDTO = new UserDTO();
            userDTO.setId(buyer.getId());
            orderDTO.setBuyer(userDTO);

            Invoice invoice = order.getInvoice();
            InvoiceDTO invoiceDTO = new InvoiceDTO();
            invoiceDTO.setInvoiceNumber(invoice.getInvoiceNumber());
            orderDTO.setInvoice(invoiceDTO);

            List<ProductOrderItem> orderItems = order.getOrderItems();
            List<ProductOrderItemDTO> orderItemDTOS = new ArrayList<>();
            orderItems.forEach(productOrderItem -> {
                ProductOrderItemDTO itemDTO = new ProductOrderItemDTO();
                itemDTO.setProductId(productOrderItem.getId());
                itemDTO.setQuantity(productOrderItem.getQuantity());
                orderItemDTOS.add(itemDTO);
            });

            orderDTO.setOrderItems(orderItemDTOS);

            dtos.add(orderDTO);
        });

        return dtos;
    }

    @GetMapping("/buyer/{bid}")
    public List<ProductDTO> getAllOrdersByBuyerId(@PathVariable Long bid) {

        List<Product> products = orderService.getAllOrdersByBuyerId(bid);

        List<ProductDTO> dts = new ArrayList<>();

        products.forEach(p -> {
            ProductDTO productDTO = new ProductDTO();

            p.getOrders().forEach(productOrderItem -> {

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

                productDTO.setOrderId(productOrderItem.getId());

                productDTO.setOrderDate(productOrderItem.getOrder().getOrderDate());
                productDTO.setProductDelivered(productOrderItem.getOrder().isDeliveryStatus());
                productDTO.setOrderQty(productOrderItem.getQuantity());
            });

            dts.add(productDTO);
        });
        return dts;
    }


}
