package com.tenneco.warehouse.web.rest;

import com.tenneco.warehouse.Application;
import com.tenneco.warehouse.domain.Orderinfo;
import com.tenneco.warehouse.repository.OrderinfoRepository;
import com.tenneco.warehouse.service.OrderinfoService;
import com.tenneco.warehouse.web.rest.dto.OrderinfoDTO;
import com.tenneco.warehouse.web.rest.mapper.OrderinfoMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the OrderinfoResource REST controller.
 *
 * @see OrderinfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OrderinfoResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_DATECREATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATECREATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATECREATE_STR = dateTimeFormatter.format(DEFAULT_DATECREATE);

    @Inject
    private OrderinfoRepository orderinfoRepository;

    @Inject
    private OrderinfoMapper orderinfoMapper;

    @Inject
    private OrderinfoService orderinfoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restOrderinfoMockMvc;

    private Orderinfo orderinfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderinfoResource orderinfoResource = new OrderinfoResource();
        ReflectionTestUtils.setField(orderinfoResource, "orderinfoService", orderinfoService);
        ReflectionTestUtils.setField(orderinfoResource, "orderinfoMapper", orderinfoMapper);
        this.restOrderinfoMockMvc = MockMvcBuilders.standaloneSetup(orderinfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        orderinfo = new Orderinfo();
        orderinfo.setDatecreate(DEFAULT_DATECREATE);
    }

    @Test
    @Transactional
    public void createOrderinfo() throws Exception {
        int databaseSizeBeforeCreate = orderinfoRepository.findAll().size();

        // Create the Orderinfo
        OrderinfoDTO orderinfoDTO = orderinfoMapper.orderinfoToOrderinfoDTO(orderinfo);

        restOrderinfoMockMvc.perform(post("/api/orderinfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderinfoDTO)))
                .andExpect(status().isCreated());

        // Validate the Orderinfo in the database
        List<Orderinfo> orderinfos = orderinfoRepository.findAll();
        assertThat(orderinfos).hasSize(databaseSizeBeforeCreate + 1);
        Orderinfo testOrderinfo = orderinfos.get(orderinfos.size() - 1);
        assertThat(testOrderinfo.getDatecreate()).isEqualTo(DEFAULT_DATECREATE);
    }

    @Test
    @Transactional
    public void getAllOrderinfos() throws Exception {
        // Initialize the database
        orderinfoRepository.saveAndFlush(orderinfo);

        // Get all the orderinfos
        restOrderinfoMockMvc.perform(get("/api/orderinfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(orderinfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].datecreate").value(hasItem(DEFAULT_DATECREATE_STR)));
    }

    @Test
    @Transactional
    public void getOrderinfo() throws Exception {
        // Initialize the database
        orderinfoRepository.saveAndFlush(orderinfo);

        // Get the orderinfo
        restOrderinfoMockMvc.perform(get("/api/orderinfos/{id}", orderinfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(orderinfo.getId().intValue()))
            .andExpect(jsonPath("$.datecreate").value(DEFAULT_DATECREATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingOrderinfo() throws Exception {
        // Get the orderinfo
        restOrderinfoMockMvc.perform(get("/api/orderinfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderinfo() throws Exception {
        // Initialize the database
        orderinfoRepository.saveAndFlush(orderinfo);

		int databaseSizeBeforeUpdate = orderinfoRepository.findAll().size();

        // Update the orderinfo
        orderinfo.setDatecreate(UPDATED_DATECREATE);
        OrderinfoDTO orderinfoDTO = orderinfoMapper.orderinfoToOrderinfoDTO(orderinfo);

        restOrderinfoMockMvc.perform(put("/api/orderinfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderinfoDTO)))
                .andExpect(status().isOk());

        // Validate the Orderinfo in the database
        List<Orderinfo> orderinfos = orderinfoRepository.findAll();
        assertThat(orderinfos).hasSize(databaseSizeBeforeUpdate);
        Orderinfo testOrderinfo = orderinfos.get(orderinfos.size() - 1);
        assertThat(testOrderinfo.getDatecreate()).isEqualTo(UPDATED_DATECREATE);
    }

    @Test
    @Transactional
    public void deleteOrderinfo() throws Exception {
        // Initialize the database
        orderinfoRepository.saveAndFlush(orderinfo);

		int databaseSizeBeforeDelete = orderinfoRepository.findAll().size();

        // Get the orderinfo
        restOrderinfoMockMvc.perform(delete("/api/orderinfos/{id}", orderinfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Orderinfo> orderinfos = orderinfoRepository.findAll();
        assertThat(orderinfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
