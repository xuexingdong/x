package com.xxd.x.admin.service.impl;

import com.sun.tools.javac.jvm.Items;
import com.xxd.x.admin.dto.ItemDTO;
import com.xxd.x.admin.service.ItemService;
import com.xxd.x.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Override
    public List<Item> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public void add(ItemDTO itemDTO) {

    }

    @Override
    public void modify(ItemDTO itemDTO) {

    }

    @Override
    public void delete(int itemId) {

    }
}
