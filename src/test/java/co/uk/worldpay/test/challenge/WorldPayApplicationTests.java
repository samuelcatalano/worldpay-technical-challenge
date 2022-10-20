package co.uk.worldpay.test.challenge;

import co.uk.worldpay.test.challenge.controller.MerchantController;
import co.uk.worldpay.test.challenge.controller.OfferController;
import co.uk.worldpay.test.challenge.enums.StatusEnum;
import co.uk.worldpay.test.challenge.exception.MerchantException;
import co.uk.worldpay.test.challenge.exception.OfferException;
import co.uk.worldpay.test.challenge.service.MerchantService;
import co.uk.worldpay.test.challenge.service.OfferService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class WorldPayApplicationTests {

    @Autowired
    private OfferController offerController;

    @Autowired
    private MerchantController merchantController;

    @Autowired
    private OfferService offerService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void contextLoads() {
        assertThat(this.offerController, notNullValue());
        assertThat(this.merchantController, notNullValue());
    }

    @Test
    @Order(2)
    public void testCreateMerchant() throws Exception {
        var json = "{\"name\":\"Samuel Catalano\"}";

        mockMvc.perform(post("/api/worldpay/merchant")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    public void testCreateOffer() throws Exception {
        var json = "{\"description\":\"New offer from Samuel\",\"currency\":\"GBP\",\"price\":45,\"creationDate\":\"2020-07-07T10:25:25\",\"expiration\":5}";

        mockMvc.perform(post("/api/worldpay/offer/merchant/1")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    public void testGetAllOffersFromSpecificMerchant() throws Exception{
        mockMvc.perform(get("/api/worldpay/offer/merchant/1")).andDo(print())
               .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    public void testCreateAnotherOffer() throws Exception {
        var json = "{\"description\":\"New offer from Catalano\",\"currency\":\"GBP\",\"price\":75,\"creationDate\":\"2020-07-07T10:25:25\",\"expiration\":15}";

        mockMvc.perform(post("/api/worldpay/offer/merchant/1")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    public void testConcludeOffer() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.put("/api/worldpay/offer/conclude/3")
               .accept(MediaType.APPLICATION_JSON)
               .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
               .andDo(print())
               .andExpect(status().isOk())
               .andReturn();

        var offer = offerService.findById(3L);
        assertThat(offer.getStatus(), is(StatusEnum.CONCLUDED));
    }

    @Test
    @Order(7)
    public void testUpdateOffer() throws Exception {
        var json = "{\"description\":\"New offer from Catalano\",\"currency\":\"EUR\",\"price\":99,\"creationDate\":\"2020-07-09T16:25:25\",\"expiration\":15}";

        var requestBuilder = MockMvcRequestBuilders.put("/api/worldpay/offer//update/1/2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        var offer = offerService.findById(2L); // offer was created at 2020-07-07T10:25:25
        assertThat(offer.getStatus(), is(StatusEnum.EXPIRED));
    }

    @Test()
    @Order(8)
    public void testGetMerchantByWrongId() {
        final MerchantException exception = assertThrows(MerchantException.class, () -> merchantService.findById(2L)); // there is no Merchant with ID 2);
        assertTrue(exception.getMessage().contains("Merchant not found with this ID"));
    }

    @Test()
    @Order(9)
    public void testConcludeOfferAlreadyConcluded() {
        final OfferException exception = assertThrows(OfferException.class, () -> offerService.concludeOffer(3L));
        assertTrue(exception.getMessage().contains("This offer is already concluded"));
    }
}