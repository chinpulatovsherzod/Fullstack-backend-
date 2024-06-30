package com.example.shopbackend.service.admin.faq;

import com.example.shopbackend.dto.FAQDto;

public interface FaqService {

    FAQDto postFaq(Long productId, FAQDto faqDto);
}
