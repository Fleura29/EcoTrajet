package com.mif10_g14_2024.mif10_g14;

import com.mif10_g14_2024.mif10_g14.dao.ItineraryRepository;
import com.mif10_g14_2024.mif10_g14.dao.TransportRepository;
import com.mif10_g14_2024.mif10_g14.dao.UserRepository;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Mif10G14ApplicationTests {

    @MockitoBean
    private ItineraryRepository itineraryRepository;
    @MockitoBean
    private TransportRepository transportRepository;
    @MockitoBean
    private UserRepository userRepository;

    /*@BeforeAll
    void setUp() {
        // Initialize the mock entity
        mockEntity = new YourEntity();
        mockEntity.setId(1L);
        mockEntity.setName("Test Entity");

        // Configure the mock repository
        given(yourRepository.findById(1L)).willReturn(Optional.of(mockEntity));
    }*/

    //@Test
    void contextLoads() {
    }

}
