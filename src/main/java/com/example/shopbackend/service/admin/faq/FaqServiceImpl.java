package com.example.shopbackend.service.admin.faq;


import com.example.shopbackend.dto.FAQDto;
import com.example.shopbackend.model.FAQ;
import com.example.shopbackend.model.Product;
import com.example.shopbackend.repository.FaqRepository;
import com.example.shopbackend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService{

    private final FaqRepository faqRepository;

    private final ProductRepository productRepository;

    public FAQDto postFaq(Long productId, FAQDto faqDto){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()){
            FAQ faq = new FAQ();

            faq.setQuestion(faqDto.getQuestion());
            faq.setAnswer(faqDto.getAnswer());
            faq.setProduct(optionalProduct.get());

            return faqRepository.save(faq).getFaqDto();
        }

        return null;
    }


}
