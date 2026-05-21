//package com.app.quantitymeasurement;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.hamcrest.Matchers.closeTo;
//import static org.hamcrest.Matchers.is;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import org.springframework.test.context.ActiveProfiles;
//
//@ActiveProfiles("test-db")
//@SpringBootTest(classes = com.app.quantitymeasurement.MeasurementApplication.class)
//@AutoConfigureMockMvc
//public class QuantityUC17Test {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    // -----------------------
//    // ADD Tests (6)
//    // -----------------------
//    @Test
//    @DisplayName("Add: 1 FEET + 12 INCH -> 2 FEET")
//    void testAddFeetAndInchToFeet() throws Exception {
//        String body = """
//            [
//              {"value":1.0,"unit":"FEET","type":"LENGTH"},
//              {"value":12.0,"unit":"INCH","type":"LENGTH"}
//            ]
//            """;
//
//        mockMvc.perform(post("/api/measurements/add?targetUnit=FEET")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.unit").value("FEET"))
//                .andExpect(jsonPath("$.value").value(2.0));
//    }
//
//    @Test
//    @DisplayName("Add: 1 YARD + 2 FEET -> 1.6666667 YARD (target YARD)")
//    void testAddYardAndFeetToYard() throws Exception {
//        String body = """
//            [
//              {"value":1.0,"unit":"YARD","type":"LENGTH"},
//              {"value":2.0,"unit":"FEET","type":"LENGTH"}
//            ]
//            """;
//
//        mockMvc.perform(post("/api/measurements/add?targetUnit=YARD")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.unit").value("YARD"))
//                .andExpect(jsonPath("$.value").isNumber());
//    }
//
//    @Test
//    @DisplayName("Add: 10 CENTIMETER + 4 INCH -> target CENTIMETER")
//    void testAddCentimeterAndInchToCentimeter() throws Exception {
//        String body = """
//            [
//              {"value":10.0,"unit":"CENTIMETER","type":"LENGTH"},
//              {"value":4.0,"unit":"INCH","type":"LENGTH"}
//            ]
//            """;
//
//        mockMvc.perform(post("/api/measurements/add?targetUnit=CENTIMETER")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.unit").value("CENTIMETER"))
//                .andExpect(jsonPath("$.value").isNumber());
//    }
//
//    @Test
//    @DisplayName("Add: 1 KILOGRAM + 500 GRAM -> 1.5 KILOGRAM")
//    void testAddKilogramAndGramToKilogram() throws Exception {
//        String body = """
//            [
//              {"value":1.0,"unit":"KILOGRAM","type":"WEIGHT"},
//              {"value":500.0,"unit":"GRAM","type":"WEIGHT"}
//            ]
//            """;
//
//        mockMvc.perform(post("/api/measurements/add?targetUnit=KILOGRAM")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.unit").value("KILOGRAM"))
//                .andExpect(jsonPath("$.value").value(1.5));
//    }
//
//    @Test
//    @DisplayName("Add: 2 POUND + 1 KILOGRAM -> target POUND")
//    void testAddPoundAndKilogramToPound() throws Exception {
//        String body = """
//            [
//              {"value":2.0,"unit":"POUND","type":"WEIGHT"},
//              {"value":1.0,"unit":"KILOGRAM","type":"WEIGHT"}
//            ]
//            """;
//
//        mockMvc.perform(post("/api/measurements/add?targetUnit=POUND")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.unit").value("POUND"))
//                .andExpect(jsonPath("$.value").isNumber());
//    }
//
//    @Test
//    @DisplayName("Add: Temperature arithmetic should return Bad Request")
//    void testAddInvalidTemperatureThrowsError() throws Exception {
//        String body = """
//            [
//              {"value":30.0,"unit":"CELSIUS","type":"TEMPERATURE"},
//              {"value":10.0,"unit":"CELSIUS","type":"TEMPERATURE"}
//            ]
//            """;
//
//        mockMvc.perform(post("/api/measurements/add?targetUnit=CELSIUS")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isBadRequest());
//    }
//
//    // -----------------------
//    // SUBTRACT Tests (5)
//    // -----------------------
//    @Test
//    @DisplayName("Subtract: 3 YARD - 1 FEET -> target FEET")
//    void testSubtractYardMinusFeetToFeet() throws Exception {
//        String body = """
//            [
//              {"value":3.0,"unit":"YARD","type":"LENGTH"},
//              {"value":1.0,"unit":"FEET","type":"LENGTH"}
//            ]
//            """;
//
//        mockMvc.perform(post("/api/measurements/subtract?targetUnit=FEET")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.unit").value("FEET"))
//                .andExpect(jsonPath("$.value").isNumber());
//    }
//
//    @Test
//    @DisplayName("Subtract: 2 KILOGRAM - 500 GRAM -> 1.5 KILOGRAM")
//    void testSubtractKilogramMinusGramToKilogram() throws Exception {
//        String body = """
//            [
//              {"value":2.0,"unit":"KILOGRAM","type":"WEIGHT"},
//              {"value":500.0,"unit":"GRAM","type":"WEIGHT"}
//            ]
//            """;
//
//        mockMvc.perform(post("/api/measurements/subtract?targetUnit=KILOGRAM")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.unit").value("KILOGRAM"))
//                .andExpect(jsonPath("$.value").isNumber());
//    }
//
//    @Test
//    @DisplayName("Subtract: 5 POUND - 1 KILOGRAM -> target POUND")
//    void testSubtractPoundMinusKilogramToPound() throws Exception {
//        String body = """
//            [
//              {"value":5.0,"unit":"POUND","type":"WEIGHT"},
//              {"value":1.0,"unit":"KILOGRAM","type":"WEIGHT"}
//            ]
//            """;
//
//        mockMvc.perform(post("/api/measurements/subtract?targetUnit=POUND")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.unit").value("POUND"))
//                .andExpect(jsonPath("$.value").isNumber());
//    }
//
//    @Test
//    @DisplayName("Subtract: 100 CENTIMETER - 10 INCH -> target CENTIMETER")
//    void testSubtractCentimeterMinusInchToCentimeter() throws Exception {
//        String body = """
//            [
//              {"value":100.0,"unit":"CENTIMETER","type":"LENGTH"},
//              {"value":10.0,"unit":"INCH","type":"LENGTH"}
//            ]
//            """;
//
//        mockMvc.perform(post("/api/measurements/subtract?targetUnit=CENTIMETER")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.unit").value("CENTIMETER"))
//                .andExpect(jsonPath("$.value").isNumber());
//    }
//
//    @Test
//    @DisplayName("Subtract: Temperature arithmetic should return Bad Request")
//    void testSubtractTemperatureThrowsError() throws Exception {
//        String body = """
//            [
//              {"value":50.0,"unit":"FAHRENHEIT","type":"TEMPERATURE"},
//              {"value":10.0,"unit":"FAHRENHEIT","type":"TEMPERATURE"}
//            ]
//            """;
//
//        mockMvc.perform(post("/api/measurements/subtract?targetUnit=FAHRENHEIT")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isBadRequest());
//    }
//
//    // -----------------------
//    // COMPARE Tests (5)
//    // -----------------------
//    @Test
//    @DisplayName("Compare: 12 INCH == 1 FEET")
//    void testCompare12InchEquals1Feet() throws Exception {
//        String body = """
//            [
//              {"value":12.0,"unit":"INCH","type":"LENGTH"},
//              {"value":1.0,"unit":"FEET","type":"LENGTH"}
//            ]
//            """;
//
//        mockMvc.perform(post("/api/measurements/compare")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.value").value(1.0));
//    }
//
//    @Test
//    @DisplayName("Compare: 3 FEET == 1 YARD")
//    void testCompare3FeetEquals1Yard() throws Exception {
//        String body = """
//            [
//              {"value":3.0,"unit":"FEET","type":"LENGTH"},
//              {"value":1.0,"unit":"YARD","type":"LENGTH"}
//            ]
//            """;
//
//        mockMvc.perform(post("/api/measurements/compare")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.value").value(1.0));
//    }
//
//    @Test
//    @DisplayName("Compare: 100 CENTIMETER == 1 METER")
//    void testCompare100CentimeterEquals1Meter() throws Exception {
//        String body = """
//            [
//              {"value":100.0,"unit":"CENTIMETER","type":"LENGTH"},
//              {"value":1.0,"unit":"METER","type":"LENGTH"}
//            ]
//            """;
//
//        mockMvc.perform(post("/api/measurements/compare")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.value").value(1.0));
//    }
//
//    @Test
//    @DisplayName("Compare: 1 KILOGRAM == 1000 GRAM")
//    void testCompare1KilogramEquals1000Gram() throws Exception {
//        String body = """
//            [
//              {"value":1.0,"unit":"KILOGRAM","type":"WEIGHT"},
//              {"value":1000.0,"unit":"GRAM","type":"WEIGHT"}
//            ]
//            """;
//
//        mockMvc.perform(post("/api/measurements/compare")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.value").value(1.0));
//    }
//
//    @Test
//    @DisplayName("Compare: Temperature conversions (Celsius vs Kelvin) should be supported or return Bad Request")
//    void testCompareTemperatureCelsiusEqualsKelvin() throws Exception {
//        String body = """
//            [
//              {"value":0.0,"unit":"CELSIUS","type":"TEMPERATURE"},
//              {"value":273.15,"unit":"KELVIN","type":"TEMPERATURE"}
//            ]
//            """;
//
//        mockMvc.perform(post("/api/measurements/compare")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk());
//    }
//
//    // -----------------------
//    // CONVERT Tests (7)
//    // -----------------------
//    @Test
//    @DisplayName("Convert: 1 FEET -> 12 INCH")
//    void testConvertFeetToInch() throws Exception {
//        String body = """
//            {"value":1.0,"unit":"FEET","type":"LENGTH"}
//            """;
//
//        mockMvc.perform(post("/api/measurements/convert?targetUnit=INCH")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.unit").value("INCH"))
//                .andExpect(jsonPath("$.value").value(12.0));
//    }
//
//    @Test
//    @DisplayName("Convert: 1 YARD -> 3 FEET")
//    void testConvertYardToFeet() throws Exception {
//        String body = """
//            {"value":1.0,"unit":"YARD","type":"LENGTH"}
//            """;
//
//        mockMvc.perform(post("/api/measurements/convert?targetUnit=FEET")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.unit").value("FEET"))
//                .andExpect(jsonPath("$.value").value(3.0));
//    }
//
//    @Test
//    @DisplayName("Convert: 30.48 CENTIMETER -> 1 FEET")
//    void testConvertCentimeterToFeet() throws Exception {
//        String body = """
//            {"value":30.48,"unit":"CENTIMETER","type":"LENGTH"}
//            """;
//
//        mockMvc.perform(post("/api/measurements/convert?targetUnit=FEET")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.unit").value("FEET"))
//                .andExpect(jsonPath("$.value").isNumber());
//    }
//
//    @Test
//    @DisplayName("Convert: 1 KILOGRAM -> 1000 GRAM")
//    void testConvertKilogramToGram() throws Exception {
//        String body = """
//            {"value":1.0,"unit":"KILOGRAM","type":"WEIGHT"}
//            """;
//
//        mockMvc.perform(post("/api/measurements/convert?targetUnit=GRAM")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.unit").value("GRAM"))
//                .andExpect(jsonPath("$.value").value(1000.0));
//    }
//
//    @Test
//    @DisplayName("Convert: 2 POUND -> target KILOGRAM (approx 0.907184)")
//    void testConvertPoundToKilogram() throws Exception {
//        String body = """
//            {"value":2.0,"unit":"POUND","type":"WEIGHT"}
//            """;
//
//        mockMvc.perform(post("/api/measurements/convert?targetUnit=KILOGRAM")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.unit").value("KILOGRAM"))
//                .andExpect(jsonPath("$.value").isNumber());
//    }
//
//    @Test
//    @DisplayName("Convert: 0 CELSIUS -> 32 FAHRENHEIT")
//    void testConvertCelsiusToFahrenheit() throws Exception {
//        String body = """
//            {"value":0.0,"unit":"CELSIUS","type":"TEMPERATURE"}
//            """;
//
//        mockMvc.perform(post("/api/measurements/convert?targetUnit=FAHRENHEIT")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.unit").value("FAHRENHEIT"))
//                .andExpect(jsonPath("$.value").isNumber());
//    }
//
//    @Test
//    @DisplayName("Convert: 273.15 KELVIN -> 0 CELSIUS")
//    void testConvertKelvinToCelsius() throws Exception {
//        String body = """
//            {"value":273.15,"unit":"KELVIN","type":"TEMPERATURE"}
//            """;
//
//        mockMvc.perform(post("/api/measurements/convert?targetUnit=CELSIUS")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.unit").value("CELSIUS"))
//                .andExpect(jsonPath("$.value").isNumber());
//    }
//
//    // -----------------------
//    // DIVIDE Tests (6)
//    // -----------------------
//    @Test
//    @DisplayName("Divide: 10 GRAM / 2 GRAM -> 5")
//    void testDivide10GramBy2Gram() throws Exception {
//        String body = """
//            [
//              {"value":10.0,"unit":"GRAM","type":"WEIGHT"},
//              {"value":2.0,"unit":"GRAM","type":"WEIGHT"}
//            ]
//            """;
//
//        mockMvc.perform(post("/api/measurements/divide")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.value").value(5.0));
//    }
//
//    @Test
//    @DisplayName("Divide: 6 FEET / 2 FEET -> 3")
//    void testDivideFeetByFeet() throws Exception {
//        String body = """
//            [
//              {"value":6.0,"unit":"FEET","type":"LENGTH"},
//              {"value":2.0,"unit":"FEET","type":"LENGTH"}
//            ]
//            """;
//
//        mockMvc.perform(post("/api/measurements/divide")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.value").value(3.0));
//    }
//
//    @Test
//    @DisplayName("Divide: 2 YARD / 1 FEET -> numeric result")
//    void testDivideYardByFeet() throws Exception {
//        String body = """
//            [
//              {"value":2.0,"unit":"YARD","type":"LENGTH"},
//              {"value":1.0,"unit":"FEET","type":"LENGTH"}
//            ]
//            """;
//
//        mockMvc.perform(post("/api/measurements/divide")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.value").isNumber());
//    }
//
//    @Test
//    @DisplayName("Divide: 1000 GRAM / 500 GRAM -> 2")
//    void testDivideKilogramByGram() throws Exception {
//        String body = """
//            [
//              {"value":1000.0,"unit":"GRAM","type":"WEIGHT"},
//              {"value":500.0,"unit":"GRAM","type":"WEIGHT"}
//            ]
//            """;
//
//        mockMvc.perform(post("/api/measurements/divide")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.value").value(2.0));
//    }
//
//    @Test
//    @DisplayName("Divide: Temperature division should return Bad Request")
//    void testDivideTemperatureThrowsError() throws Exception {
//        String body = """
//            [
//              {"value":100.0,"unit":"CELSIUS","type":"TEMPERATURE"},
//              {"value":2.0,"unit":"CELSIUS","type":"TEMPERATURE"}
//            ]
//            """;
//
//        mockMvc.perform(post("/api/measurements/divide")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @DisplayName("Divide: Division by zero should return Bad Request")
//    void testDivideByZeroThrowsError() throws Exception {
//        String body = """
//            [
//              {"value":10.0,"unit":"GRAM","type":"WEIGHT"},
//              {"value":0.0,"unit":"GRAM","type":"WEIGHT"}
//            ]
//            """;
//
//        mockMvc.perform(post("/api/measurements/divide")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isBadRequest());
//    }
//}
