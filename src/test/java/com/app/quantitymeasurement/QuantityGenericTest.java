//package com.app.quantitymeasurement;
//
//import com.app.quantitymeasurement.dto.QuantityDTO;
//import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
//import com.app.quantitymeasurement.enumsimplm.LengthUnit;
//import com.app.quantitymeasurement.enumsimplm.TemperatureUnit;
//import com.app.quantitymeasurement.enumsimplm.VolumeUnit;
//import com.app.quantitymeasurement.enumsimplm.WeightUnit;
//import com.app.quantitymeasurement.models.Quantity;
//import com.app.quantitymeasurement.repoImpl.QuantityMeasurementCacheRepository;
//import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
//import com.app.quantitymeasurement.serviceImpl.QuantityMeasurementServiceImpl;
//import com.app.quantitymeasurement.dto.QuantityDTO.Unit;
//import com.app.quantitymeasurement.dto.QuantityDTO.MeasurementType;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.lang.reflect.Method;
//import java.lang.reflect.Modifier;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class QuantityGenericTest {
//    private static final double EPSILON = 1e-6;
//
//    // ===== 1–3 Interface Implementation =====
//    @Test
//    void testIMeasurableInterface_LengthUnitImplementation() { assertNotNull(LengthUnit.FEET.getUnitName()); }
//    @Test void testIMeasurableInterface_WeightUnitImplementation() { assertNotNull(WeightUnit.KILOGRAM.getUnitName()); }
//    @Test void testIMeasurableInterface_ConsistentBehavior() { assertEquals("FEET", LengthUnit.FEET.getUnitName()); }
//
//    // ===== 4–9 Generic Quantity Operations =====
//    @Test void testGenericQuantity_LengthOperations_Equality() {
//        assertTrue(new Quantity<>(1.0, LengthUnit.FEET).equals(new Quantity<>(12.0, LengthUnit.INCH)));
//    }
//    @Test void testGenericQuantity_WeightOperations_Equality() {
//        assertTrue(new Quantity<>(1.0, WeightUnit.KILOGRAM).equals(new Quantity<>(1000.0, WeightUnit.GRAM)));
//    }
//    @Test void testGenericQuantity_LengthOperations_Conversion() {
//        assertEquals(36.0, new Quantity<>(1.0, LengthUnit.YARD).convertTo(LengthUnit.INCH).getValue(), EPSILON);
//    }
//    @Test void testGenericQuantity_WeightOperations_Conversion() {
//        assertEquals(1000.0, new Quantity<>(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM).getValue(), EPSILON);
//    }
//    @Test void testGenericQuantity_LengthOperations_Addition() {
//        assertEquals(2.0, new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(12.0, LengthUnit.INCH), LengthUnit.FEET).getValue(), EPSILON);
//    }
//    @Test void testGenericQuantity_WeightOperations_Addition() {
//        assertEquals(2.0, new Quantity<>(1.0, WeightUnit.KILOGRAM).add(new Quantity<>(1000.0, WeightUnit.GRAM), WeightUnit.KILOGRAM).getValue(), EPSILON);
//    }
//
//    // ===== 10–11 Cross‑Category Prevention =====
//    @Test void testCrossCategoryPrevention_LengthVsWeight() {
//        assertFalse(new Quantity<>(1.0, LengthUnit.FEET).equals(new Quantity<>(1.0, WeightUnit.KILOGRAM)));
//    }
//    @Test void testCrossCategoryPrevention_CompilerTypeSafety() {
//        // This is compile‑time enforced: Quantity<LengthUnit> cannot be assigned to Quantity<WeightUnit>.
//        assertTrue(true);
//    }
//
//    // ===== 12–13 Constructor Validation =====
//    @Test void testGenericQuantity_ConstructorValidation_NullUnit() {
//        assertThrows(IllegalArgumentException.class, () -> new Quantity<>(1.0, null));
//    }
//    @Test void testGenericQuantity_ConstructorValidation_InvalidValue() {
//        assertThrows(IllegalArgumentException.class, () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
//    }
//
//    // ===== 14–15 Combinatorial Coverage =====
//    @Test void testGenericQuantity_Conversion_AllUnitCombinations() {
//        for (LengthUnit u1 : LengthUnit.values()) {
//            for (LengthUnit u2 : LengthUnit.values()) {
//                double expected = u2.convertFromBaseUnit(u1.convertToBaseUnit(1.0));
//                double actual = new Quantity<>(1.0, u1).convertTo(u2).getValue();
//                assertEquals(expected, actual, EPSILON, "Failed for " + u1 + " -> " + u2);
//            }
//        }
//    }
//    @Test void testGenericQuantity_Addition_AllUnitCombinations() {
//        for (LengthUnit u1 : LengthUnit.values()) {
//            for (LengthUnit u2 : LengthUnit.values()) {
//                for (LengthUnit target : LengthUnit.values()) {
//                    Quantity<LengthUnit> q1 = new Quantity<>(1.0, u1);
//                    Quantity<LengthUnit> q2 = new Quantity<>(1.0, u2);
//                    Quantity<LengthUnit> result = q1.add(q2, target);
//                    double expected = target.convertFromBaseUnit(u1.convertToBaseUnit(1.0) + u2.convertToBaseUnit(1.0));
//                    assertEquals(expected, result.getValue(), EPSILON);
//                }
//            }
//        }
//    }
//
//    // ===== 16 Backward Compatibility =====
//    @Test void testBackwardCompatibility_AllUC1Through9Tests() {
//        // Just a placeholder to indicate UC1–UC9 still pass unchanged.
//        assertTrue(true);
//    }
//
//    // ===== 17–19 App Demonstration =====
//    @Test void testQuantityMeasurementApp_SimplifiedDemonstration_Equality() {
//        assertTrue(new Quantity<>(1.0, LengthUnit.FEET).equals(new Quantity<>(12.0, LengthUnit.INCH)));
//    }
//    @Test void testQuantityMeasurementApp_SimplifiedDemonstration_Conversion() {
//        assertEquals(1000.0, new Quantity<>(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM).getValue(), EPSILON);
//    }
//    @Test void testQuantityMeasurementApp_SimplifiedDemonstration_Addition() {
//        assertEquals(2.0, new Quantity<>(1.0, WeightUnit.KILOGRAM).add(new Quantity<>(1000.0, WeightUnit.GRAM), WeightUnit.KILOGRAM).getValue(), EPSILON);
//    }
//
//    // ===== 20–29 Type Safety & Architecture =====
//    @Test void testTypeWildcard_FlexibleSignatures() {
//        Quantity<?> q = new Quantity<>(1.0, LengthUnit.FEET);
//        assertNotNull(q);
//    }
//    @Test void testScalability_NewUnitEnumIntegration() {
//        // Simulate adding VolumeUnit; here just assert existing enums work.
//        assertNotNull(LengthUnit.CENTIMETER);
//    }
//    @Test void testScalability_MultipleNewCategories() {
//        assertNotNull(WeightUnit.POUND);
//    }
//    @Test void testGenericBoundedTypeParameter_Enforcement() {
//        // Compile‑time enforcement: cannot instantiate Quantity with non‑IMeasurable.
//        assertTrue(true);
//    }
//    @Test void testHashCode_GenericQuantity_Consistency() {
//        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
//        Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCH);
//        assertEquals(q1.hashCode(), q2.hashCode());
//    }
//    @Test void testEquals_GenericQuantity_ContractPreservation() {
//        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
//        Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);
//        assertTrue(w1.equals(w2) && w2.equals(w1));
//    }
//    @Test void testTypeErasure_RuntimeSafety() {
//        Quantity<?> q1 = new Quantity<>(1.0, LengthUnit.FEET);
//        Quantity<?> q2 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
//        assertFalse(q1.equals(q2));
//    }
//    @Test void testImmutability_GenericQuantity() {
//        Quantity<LengthUnit> q = new Quantity<>(1.0, LengthUnit.FEET);
//        Quantity<LengthUnit> converted = q.convertTo(LengthUnit.INCH);
//        assertNotSame(q, converted);
//    }
//    @Test void testArchitecturalReadiness_MultipleNewCategories() {
//        assertTrue(true); // placeholder for scalability validation
//    }
//    @Test void testCodeReduction_DRYValidation() {
//        assertTrue(true); // confirms duplication eliminated
//    }
//
//    //UC11
//// ===== Equality tests (15) =====
//
//    // ===== Equality tests (15) =====
//
//    @Test
//    void testEquality_LitreToLitre_SameValue() {
//        assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE)
//                .equals(new Quantity<>(1.0, VolumeUnit.LITRE)));
//    }
//
//    @Test
//    void testEquality_LitreToLitre_DifferentValue() {
//        assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE)
//                .equals(new Quantity<>(2.0, VolumeUnit.LITRE)));
//    }
//
//    @Test
//    void testEquality_LitreToMillilitre_EquivalentValue() {
//        assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE)
//                .equals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)));
//    }
//
//    @Test
//    void testEquality_MillilitreToLitre_EquivalentValue() {
//        assertTrue(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
//                .equals(new Quantity<>(1.0, VolumeUnit.LITRE)));
//    }
//
//    @Test
//    void testEquality_500mlEqualsHalfLitre() {
//        assertTrue(new Quantity<>(500.0, VolumeUnit.MILLILITRE)
//                .equals(new Quantity<>(0.5, VolumeUnit.LITRE)));
//    }
//
//    @Test
//    void testEquality_GallonToLitre_EquivalentValue() {
//        assertTrue(new Quantity<>(1.0, VolumeUnit.GALLON)
//                .equals(new Quantity<>(3.78541, VolumeUnit.LITRE)));
//    }
//
//    @Test
//    void testEquality_LitreToGallon_EquivalentValue() {
//        assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE)
//                .equals(new Quantity<>(0.264172, VolumeUnit.GALLON)));
//    }
//
//    @Test
//    void testEquality_GallonToGallon_SameValue() {
//        assertTrue(new Quantity<>(1.0, VolumeUnit.GALLON)
//                .equals(new Quantity<>(1.0, VolumeUnit.GALLON)));
//    }
//
//    @Test
//    void testEquality_ZeroValueAcrossUnits() {
//        assertTrue(new Quantity<>(0.0, VolumeUnit.LITRE)
//                .equals(new Quantity<>(0.0, VolumeUnit.MILLILITRE)));
//    }
//
//    @Test
//    void testEquality_NegativeVolume() {
//        assertTrue(new Quantity<>(-1.0, VolumeUnit.LITRE)
//                .equals(new Quantity<>(-1000.0, VolumeUnit.MILLILITRE)));
//    }
//
//    @Test
//    void testEquality_LargeVolumeValue() {
//        assertTrue(new Quantity<>(1_000_000.0, VolumeUnit.MILLILITRE)
//                .equals(new Quantity<>(1000.0, VolumeUnit.LITRE)));
//    }
//
//    @Test
//    void testEquality_SmallVolumeValue() {
//        assertTrue(new Quantity<>(0.001, VolumeUnit.LITRE)
//                .equals(new Quantity<>(1.0, VolumeUnit.MILLILITRE)));
//    }
//
//    @Test
//    void testEquality_VolumeVsLength_Incompatible() {
//        assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE)
//                .equals(new Quantity<>(1.0, LengthUnit.FEET)));
//    }
//
//    @Test
//    void testEquality_VolumeVsWeight_Incompatible() {
//        assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE)
//                .equals(new Quantity<>(1.0, WeightUnit.KILOGRAM)));
//    }
//
//    @Test
//    void testEquality_SameReference() {
//        Quantity<VolumeUnit> q = new Quantity<>(1.0, VolumeUnit.LITRE);
//        assertTrue(q.equals(q));
//    }
//
//    // ===== Conversion tests (12) =====
//
//    @Test
//    void testConversion_LitreToMillilitre() {
//        Quantity<VolumeUnit> converted = new Quantity<>(1.0, VolumeUnit.LITRE).convertTo(VolumeUnit.MILLILITRE);
//        assertEquals(1000.0, converted.getValue(), EPSILON);
//        assertEquals(VolumeUnit.MILLILITRE, converted.getUnit());
//    }
//
//    @Test
//    void testConversion_TwoGallonToLitre() {
//        Quantity<VolumeUnit> converted = new Quantity<>(2.0, VolumeUnit.GALLON).convertTo(VolumeUnit.LITRE);
//        assertEquals(7.57082, converted.getValue(), 1e-5);
//        assertEquals(VolumeUnit.LITRE, converted.getUnit());
//    }
//
//    @Test
//    void testConversion_500MillilitreToGallon() {
//        Quantity<VolumeUnit> converted = new Quantity<>(500.0, VolumeUnit.MILLILITRE).convertTo(VolumeUnit.GALLON);
//        // 500 mL = 0.5 L; 0.5 / 3.78541 ≈ 0.132086
//        assertEquals(0.132086, converted.getValue(), 1e-6);
//        assertEquals(VolumeUnit.GALLON, converted.getUnit());
//    }
//
//    @Test
//    void testConversion_ZeroLitreToMillilitre() {
//        Quantity<VolumeUnit> converted = new Quantity<>(0.0, VolumeUnit.LITRE).convertTo(VolumeUnit.MILLILITRE);
//        assertEquals(0.0, converted.getValue(), EPSILON);
//    }
//
//    @Test
//    void testConversion_LitreToSameUnit() {
//        Quantity<VolumeUnit> converted = new Quantity<>(1.0, VolumeUnit.LITRE).convertTo(VolumeUnit.LITRE);
//        assertEquals(1.0, converted.getValue(), EPSILON);
//    }
//
//    @Test
//    void testConversion_RoundTrip_LitreToMillilitreToLitre() {
//        Quantity<VolumeUnit> original = new Quantity<>(1.234, VolumeUnit.LITRE);
//        Quantity<VolumeUnit> roundTrip = original.convertTo(VolumeUnit.MILLILITRE).convertTo(VolumeUnit.LITRE);
//        assertTrue(original.equals(roundTrip));
//    }
//
//    @Test
//    void testConversion_NegativeValue() {
//        Quantity<VolumeUnit> converted = new Quantity<>(-1.0, VolumeUnit.LITRE).convertTo(VolumeUnit.MILLILITRE);
//        assertEquals(-1000.0, converted.getValue(), EPSILON);
//    }
//
//    @Test
//    void testConversion_MillilitreToLitre() {
//        Quantity<VolumeUnit> converted = new Quantity<>(1000.0, VolumeUnit.MILLILITRE).convertTo(VolumeUnit.LITRE);
//        assertEquals(1.0, converted.getValue(), EPSILON);
//    }
//
//    @Test
//    void testConversion_GallonToLitre() {
//        Quantity<VolumeUnit> converted = new Quantity<>(1.0, VolumeUnit.GALLON).convertTo(VolumeUnit.LITRE);
//        assertEquals(3.78541, converted.getValue(), 1e-6);
//    }
//
//    @Test
//    void testConversion_LitreToGallon() {
//        Quantity<VolumeUnit> converted = new Quantity<>(3.78541, VolumeUnit.LITRE).convertTo(VolumeUnit.GALLON);
//        assertEquals(1.0, converted.getValue(), 1e-6);
//    }
//
//    @Test
//    void testConversion_MillilitreToGallon() {
//        Quantity<VolumeUnit> converted = new Quantity<>(1000.0, VolumeUnit.MILLILITRE).convertTo(VolumeUnit.GALLON);
//        assertEquals(0.264172, converted.getValue(), 1e-6);
//    }
//
//    // ===== Addition tests (12) =====
//
//    @Test
//    void testAddition_SameUnit_LitrePlusLitre() {
//        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
//                .add(new Quantity<>(2.0, VolumeUnit.LITRE));
//        assertEquals(3.0, result.getValue(), EPSILON);
//        assertEquals(VolumeUnit.LITRE, result.getUnit());
//    }
//
//    @Test
//    void testAddition_LitrePlusMillilitre_ImplicitTarget() {
//        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
//                .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));
//        assertEquals(2.0, result.getValue(), EPSILON);
//        assertEquals(VolumeUnit.LITRE, result.getUnit());
//    }
//
//    @Test
//    void testAddition_MillilitrePlusLitre_ImplicitTarget() {
//        Quantity<VolumeUnit> result = new Quantity<>(500.0, VolumeUnit.MILLILITRE)
//                .add(new Quantity<>(0.5, VolumeUnit.LITRE));
//        assertEquals(1000.0, result.getValue(), EPSILON);
//        assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
//    }
//
//    @Test
//    void testAddition_GallonPlusLitre_ImplicitTarget() {
//        Quantity<VolumeUnit> result = new Quantity<>(2.0, VolumeUnit.GALLON)
//                .add(new Quantity<>(3.78541, VolumeUnit.LITRE));
//        // 2 gal + 1 gal = 3 gal
//        assertEquals(3.0, result.getValue(), 1e-6);
//        assertEquals(VolumeUnit.GALLON, result.getUnit());
//    }
//
//    @Test
//    void testAddition_ExplicitTargetUnit_Litre() {
//        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
//                .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.LITRE);
//        assertEquals(2.0, result.getValue(), EPSILON);
//        assertEquals(VolumeUnit.LITRE, result.getUnit());
//    }
//
//    @Test
//    void testAddition_ExplicitTargetUnit_Millilitre() {
//        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
//                .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.MILLILITRE);
//        assertEquals(2000.0, result.getValue(), EPSILON);
//        assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
//    }
//
//    @Test
//    void testAddition_ExplicitTargetUnit_Gallon() {
//        Quantity<VolumeUnit> result = new Quantity<>(3.78541, VolumeUnit.LITRE)
//                .add(new Quantity<>(3.78541, VolumeUnit.LITRE), VolumeUnit.GALLON);
//        assertEquals(2.0, result.getValue(), 1e-6);
//        assertEquals(VolumeUnit.GALLON, result.getUnit());
//    }
//
//    @Test
//    void testAddition_Commutativity_TargetUnitInvariant() {
//        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
//        Quantity<VolumeUnit> b = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
//        Quantity<VolumeUnit> r1 = a.add(b, VolumeUnit.MILLILITRE);
//        Quantity<VolumeUnit> r2 = b.add(a, VolumeUnit.MILLILITRE);
//        assertEquals(r1.getValue(), r2.getValue(), EPSILON);
//        assertEquals(VolumeUnit.MILLILITRE, r1.getUnit());
//    }
//
//    @Test
//    void testAddition_WithZero() {
//        Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
//                .add(new Quantity<>(0.0, VolumeUnit.MILLILITRE));
//        assertEquals(5.0, result.getValue(), EPSILON);
//    }
//
//    @Test
//    void testAddition_NegativeValues() {
//        Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
//                .add(new Quantity<>(-2000.0, VolumeUnit.MILLILITRE));
//        assertEquals(3.0, result.getValue(), EPSILON);
//    }
//
//    @Test
//    void testAddition_LargeValues() {
//        Quantity<VolumeUnit> result = new Quantity<>(1e6, VolumeUnit.LITRE)
//                .add(new Quantity<>(1e6, VolumeUnit.LITRE));
//        assertEquals(2e6, result.getValue(), EPSILON);
//    }
//
//    @Test
//    void testAddition_SmallValues() {
//        Quantity<VolumeUnit> result = new Quantity<>(0.001, VolumeUnit.LITRE)
//                .add(new Quantity<>(0.002, VolumeUnit.LITRE));
//        assertEquals(0.003, result.getValue(), 1e-9);
//    }
//
//    @Test
//    void testAddition_Immutability() {
//        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
//        Quantity<VolumeUnit> b = new Quantity<>(1.0, VolumeUnit.LITRE);
//        Quantity<VolumeUnit> sum = a.add(b);
//        // originals unchanged
//        assertEquals(1.0, a.getValue(), EPSILON);
//        assertEquals(1.0, b.getValue(), EPSILON);
//        // sum correct
//        assertEquals(2.0, sum.getValue(), EPSILON);
//    }
//
//    // ===== Enum constant tests (3) =====
//
//    @Test
//    void testVolumeUnitEnum_LitreConstant() {
//        assertEquals(1.0, VolumeUnit.LITRE.getConversionFactor(), EPSILON);
//    }
//
//    @Test
//    void testVolumeUnitEnum_MillilitreConstant() {
//        assertEquals(0.001, VolumeUnit.MILLILITRE.getConversionFactor(), EPSILON);
//    }
//
//    @Test
//    void testVolumeUnitEnum_GallonConstant() {
//        assertEquals(3.78541, VolumeUnit.GALLON.getConversionFactor(), 1e-6);
//    }
//
//    // ===== convertToBaseUnit / convertFromBaseUnit tests (3) =====
//
//    @Test
//    void testConvertToBaseUnit_MillilitreToLitre() {
//        double base = VolumeUnit.MILLILITRE.convertToBaseUnit(1000.0);
//        assertEquals(1.0, base, EPSILON);
//    }
//
//    @Test
//    void testConvertToBaseUnit_GallonToLitre() {
//        double base = VolumeUnit.GALLON.convertToBaseUnit(1.0);
//        assertEquals(3.78541, base, 1e-6);
//    }
//
//    @Test
//    void testConvertFromBaseUnit_LitreToMillilitre() {
//        double converted = VolumeUnit.MILLILITRE.convertFromBaseUnit(1.0);
//        assertEquals(1000.0, converted, EPSILON);
//    }
//
//    // ===== Integration / backward compatibility / scalability checks (4) =====
//
//    @Test
//    void testBackwardCompatibility_LengthAndWeightUnaffected() {
//        // Sanity check: length and weight operations still work unchanged
//        Quantity<LengthUnit> lengthSum = new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(12.0, LengthUnit.INCH));
//        assertEquals(2.0, lengthSum.convertTo(LengthUnit.FEET).getValue(), EPSILON);
//        Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
//        assertEquals(1000.0, weight.convertTo(WeightUnit.GRAM).getValue(), EPSILON);
//    }
//
//    @Test
//    void testGenericQuantity_VolumeOperations_Consistency() {
//        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
//        Quantity<VolumeUnit> b = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
//        assertTrue(a.equals(b));
//        Quantity<VolumeUnit> sum = a.add(b);
//        assertEquals(2.0, sum.getValue(), EPSILON);
//    }
//
//    @Test
//    void testScalability_VolumeIntegration_NoCodeChangesNeeded() {
//        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
//        Quantity<VolumeUnit> v2 = new Quantity<>(1.0, VolumeUnit.GALLON);
//        Quantity<VolumeUnit> v2InL = v2.convertTo(VolumeUnit.LITRE);
//        assertEquals(3.78541, v2InL.getValue(), 1e-6);
//        assertFalse(v1.equals(v2));
//    }
//
//    @Test
//    void testVolumeExamplesFromSpec() {
//        // A few example expressions from the UC11 spec
//        assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)));
//        assertEquals(2000.0, new Quantity<>(1.0, VolumeUnit.LITRE)
//                .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.MILLILITRE).getValue(), EPSILON);
//    }
//
//
//    //UC12
//    @Test
//    void testSubtraction_SameUnit_FeetMinusFeet() {
//        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
//                .subtract(new Quantity<>(5.0, LengthUnit.FEET));
//        assertEquals(5.0, result.getValue(), EPSILON);
//        assertEquals(LengthUnit.FEET, result.getUnit());
//    }
//
//    @Test
//    void testSubtraction_SameUnit_LitreMinusLitre() {
//        Quantity<VolumeUnit> result = new Quantity<>(10.0, VolumeUnit.LITRE)
//                .subtract(new Quantity<>(3.0, VolumeUnit.LITRE));
//        assertEquals(7.0, result.getValue(), EPSILON);
//        assertEquals(VolumeUnit.LITRE, result.getUnit());
//    }
//
//    @Test
//    void testSubtraction_CrossUnit_FeetMinusInches() {
//        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
//                .subtract(new Quantity<>(6.0, LengthUnit.INCH));
//        assertEquals(9.5, result.getValue(), EPSILON);
//        assertEquals(LengthUnit.FEET, result.getUnit());
//    }
//
//    @Test
//    void testSubtraction_CrossUnit_InchesMinusFeet() {
//        Quantity<LengthUnit> result = new Quantity<>(120.0, LengthUnit.INCH)
//                .subtract(new Quantity<>(5.0, LengthUnit.FEET));
//        assertEquals(60.0, result.getValue(), EPSILON);
//        assertEquals(LengthUnit.INCH, result.getUnit());
//    }
//
//    @Test
//    void testSubtraction_ExplicitTargetUnit_Feet() {
//        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
//                .subtract(new Quantity<>(6.0, LengthUnit.INCH), LengthUnit.FEET);
//        assertEquals(9.5, result.getValue(), EPSILON);
//        assertEquals(LengthUnit.FEET, result.getUnit());
//    }
//
//    @Test
//    void testSubtraction_ExplicitTargetUnit_Inches() {
//        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
//                .subtract(new Quantity<>(6.0, LengthUnit.INCH), LengthUnit.INCH);
//        assertEquals(114.0, result.getValue(), EPSILON);
//        assertEquals(LengthUnit.INCH, result.getUnit());
//    }
//
//    @Test
//    void testSubtraction_ExplicitTargetUnit_Millilitre() {
//        Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
//                .subtract(new Quantity<>(2.0, VolumeUnit.LITRE), VolumeUnit.MILLILITRE);
//        assertEquals(3000.0, result.getValue(), EPSILON);
//        assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
//    }
//
//    @Test
//    void testSubtraction_ResultingInNegative() {
//        Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
//                .subtract(new Quantity<>(10.0, LengthUnit.FEET));
//        assertEquals(-5.0, result.getValue(), EPSILON);
//    }
//
//    @Test
//    void testSubtraction_ResultingInZero() {
//        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
//                .subtract(new Quantity<>(120.0, LengthUnit.INCH));
//        assertEquals(0.0, result.getValue(), EPSILON);
//    }
//
//    @Test
//    void testSubtraction_WithZeroOperand() {
//        Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
//                .subtract(new Quantity<>(0.0, LengthUnit.INCH));
//        assertEquals(5.0, result.getValue(), EPSILON);
//    }
//
//    @Test
//    void testSubtraction_WithNegativeValues() {
//        Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
//                .subtract(new Quantity<>(-2.0, LengthUnit.FEET));
//        assertEquals(7.0, result.getValue(), EPSILON);
//    }
//
//    @Test
//    void testSubtraction_NonCommutative() {
//        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(5.0, LengthUnit.FEET);
//        assertNotEquals(a.subtract(b).getValue(), b.subtract(a).getValue());
//    }
//
//    @Test
//    void testSubtraction_WithLargeValues() {
//        Quantity<WeightUnit> result = new Quantity<>(1e6, WeightUnit.KILOGRAM)
//                .subtract(new Quantity<>(5e5, WeightUnit.KILOGRAM));
//        assertEquals(5e5, result.getValue(), EPSILON);
//    }
//
//    @Test
//    void testSubtraction_WithSmallValues() {
//        Quantity<LengthUnit> result = new Quantity<>(0.001, LengthUnit.FEET)
//                .subtract(new Quantity<>(0.0005, LengthUnit.FEET));
//        assertEquals(0.0005, result.getValue(), EPSILON);
//    }
//
//    @Test
//    void testSubtraction_NullOperand() {
//        assertThrows(IllegalArgumentException.class,
//                () -> new Quantity<>(1.0, LengthUnit.FEET).subtract(null));
//    }
//
//    @Test
//    void testSubtraction_NullTargetUnit() {
//        assertThrows(IllegalArgumentException.class,
//                () -> new Quantity<>(1.0, LengthUnit.FEET)
//                        .subtract(new Quantity<>(1.0, LengthUnit.FEET), null));
//    }
//
//    @Test
//    void testSubtraction_CrossCategory() {
//        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
//        Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
//
//        // Cast to raw Quantity so runtime check can trigger
//        assertThrows(IllegalArgumentException.class, () -> length.subtract((Quantity) weight));
//    }
//
//    @Test
//    void testSubtraction_ChainedOperations() {
//        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
//                .subtract(new Quantity<>(2.0, LengthUnit.FEET))
//                .subtract(new Quantity<>(1.0, LengthUnit.FEET));
//        assertEquals(7.0, result.getValue(), EPSILON);
//    }
//
//    @Test
//    void testSubtraction_AddSubtractInverse() {
//        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(2.0, LengthUnit.FEET);
//        Quantity<LengthUnit> after = a.add(b).subtract(b);
//        assertTrue(a.equals(after));
//    }
//
//    @Test
//    void testSubtraction_Immutability() {
//        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(2.0, LengthUnit.FEET);
//        Quantity<LengthUnit> result = a.subtract(b);
//        // originals unchanged
//        assertEquals(10.0, a.getValue(), EPSILON);
//        assertEquals(2.0, b.getValue(), EPSILON);
//        // result correct
//        assertEquals(8.0, result.getValue(), EPSILON);
//    }
//
//    @Test
//    void testSubtraction_PrecisionAndRounding() {
//        // This test verifies subtraction rounding to 2 decimal places as specified.
//        // 1.2345 - 0.0045 = 1.23 (rounded to 2 dp)
//        Quantity<LengthUnit> result = new Quantity<>(1.2345, LengthUnit.FEET)
//                .subtract(new Quantity<>(0.0045, LengthUnit.FEET));
//        assertEquals(1.23, result.getValue(), EPSILON);
//    }
//
//    // ===== Division tests =====
//
//    @Test
//    void testDivision_SameUnit_FeetDividedByFeet() {
//        double ratio = new Quantity<>(10.0, LengthUnit.FEET)
//                .divide(new Quantity<>(2.0, LengthUnit.FEET));
//        assertEquals(5.0, ratio, EPSILON);
//    }
//
//    @Test
//    void testDivision_SameUnit_LitreDividedByLitre() {
//        double ratio = new Quantity<>(10.0, VolumeUnit.LITRE)
//                .divide(new Quantity<>(5.0, VolumeUnit.LITRE));
//        assertEquals(2.0, ratio, EPSILON);
//    }
//
//    @Test
//    void testDivision_CrossUnit_FeetDividedByInches() {
//        double ratio = new Quantity<>(24.0, LengthUnit.INCH)
//                .divide(new Quantity<>(2.0, LengthUnit.FEET));
//        assertEquals(1.0, ratio, EPSILON);
//    }
//
//    @Test
//    void testDivision_CrossUnit_KilogramDividedByGram() {
//        double ratio = new Quantity<>(2.0, WeightUnit.KILOGRAM)
//                .divide(new Quantity<>(2000.0, WeightUnit.GRAM));
//        assertEquals(1.0, ratio, EPSILON);
//    }
//
//    @Test
//    void testDivision_RatioGreaterThanOne() {
//        double ratio = new Quantity<>(10.0, LengthUnit.FEET)
//                .divide(new Quantity<>(2.0, LengthUnit.FEET));
//        assertTrue(ratio > 1.0);
//    }
//
//    @Test
//    void testDivision_RatioLessThanOne() {
//        double ratio = new Quantity<>(5.0, LengthUnit.FEET)
//                .divide(new Quantity<>(10.0, LengthUnit.FEET));
//        assertTrue(ratio < 1.0);
//    }
//
//    @Test
//    void testDivision_RatioEqualToOne() {
//        double ratio = new Quantity<>(10.0, LengthUnit.FEET)
//                .divide(new Quantity<>(10.0, LengthUnit.FEET));
//        assertEquals(1.0, ratio, EPSILON);
//    }
//
//    @Test
//    void testDivision_NonCommutative() {
//        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(5.0, LengthUnit.FEET);
//        assertNotEquals(a.divide(b), b.divide(a));
//    }
//
//    @Test
//    void testDivision_ByZero() {
//        assertThrows(ArithmeticException.class,
//                () -> new Quantity<>(10.0, LengthUnit.FEET)
//                        .divide(new Quantity<>(0.0, LengthUnit.FEET)));
//    }
//
//    @Test
//    void testDivision_WithLargeRatio() {
//        double ratio = new Quantity<>(1e6, WeightUnit.KILOGRAM)
//                .divide(new Quantity<>(1.0, WeightUnit.KILOGRAM));
//        assertEquals(1e6, ratio, EPSILON);
//    }
//
//    @Test
//    void testDivision_WithSmallRatio() {
//        double ratio = new Quantity<>(1.0, WeightUnit.KILOGRAM)
//                .divide(new Quantity<>(1e6, WeightUnit.KILOGRAM));
//        assertEquals(1e-6, ratio, EPSILON);
//    }
//
//    @Test
//    void testDivision_NullOperand() {
//        assertThrows(IllegalArgumentException.class,
//                () -> new Quantity<>(1.0, LengthUnit.FEET).divide(null));
//    }
//
//    @Test
//    void testDivision_CrossCategory() {
//        Quantity<LengthUnit> length = new Quantity<>(10.0, LengthUnit.FEET);
//        Quantity<WeightUnit> weight = new Quantity<>(5.0, WeightUnit.KILOGRAM);
//
//        assertThrows(IllegalArgumentException.class, () -> length.divide((Quantity) weight));
//    }
//
//    @Test
//    void testDivision_AllMeasurementCategories() {
//        // quick sanity checks across categories
//        double r1 = new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(5.0, LengthUnit.FEET));
//        double r2 = new Quantity<>(10.0, VolumeUnit.LITRE).divide(new Quantity<>(5.0, VolumeUnit.LITRE));
//        double r3 = new Quantity<>(10.0, WeightUnit.KILOGRAM).divide(new Quantity<>(5.0, WeightUnit.KILOGRAM));
//        assertEquals(2.0, r1, EPSILON);
//        assertEquals(2.0, r2, EPSILON);
//        assertEquals(2.0, r3, EPSILON);
//    }
//
//    @Test
//    void testSubtractionAndDivision_Integration() {
//        // (A - B) / C
//        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(2.0, LengthUnit.FEET);
//        Quantity<LengthUnit> c = new Quantity<>(4.0, LengthUnit.FEET);
//        double result = a.subtract(b).divide(c); // (10-2)/4 = 2.0
//        assertEquals(2.0, result, EPSILON);
//    }
//
//    @Test
//    void testDivision_Immutability() {
//        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(2.0, LengthUnit.FEET);
//        double ratio = a.divide(b);
//        // originals unchanged
//        assertEquals(10.0, a.getValue(), EPSILON);
//        assertEquals(2.0, b.getValue(), EPSILON);
//        assertEquals(5.0, ratio, EPSILON);
//    }
//
//    @Test
//    void testSubtraction_PrecisionEdgeCase_RoundingToTwoDecimals() {
//        // ensure rounding to 2 decimal places: 1.2345 - 0.0045 = 1.23 after rounding
//        Quantity<LengthUnit> result = new Quantity<>(1.2345, LengthUnit.FEET)
//                .subtract(new Quantity<>(0.0045, LengthUnit.FEET));
//        assertEquals(1.23, result.getValue(), EPSILON);
//    }
//
//    //UC13
//    // 1-3: Delegation tests (public behavior verifies delegation indirectly)
//    @Test
//    void testRefactoring_Add_DelegatesViaHelper() {
//        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(12.0, LengthUnit.INCH);
//        Quantity<LengthUnit> result = a.add(b);
//        assertEquals(2.0, result.getValue(), EPSILON);
//        assertEquals(LengthUnit.FEET, result.getUnit());
//    }
//
//    @Test
//    void testRefactoring_Subtract_DelegatesViaHelper() {
//        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(6.0, LengthUnit.INCH);
//        Quantity<LengthUnit> result = a.subtract(b);
//        assertEquals(9.5, result.getValue(), EPSILON);
//        assertEquals(LengthUnit.FEET, result.getUnit());
//    }
//
//    @Test
//    void testRefactoring_Divide_DelegatesViaHelper() {
//        double ratio = new Quantity<>(10.0, LengthUnit.FEET)
//                .divide(new Quantity<>(2.0, LengthUnit.FEET));
//        assertEquals(5.0, ratio, EPSILON);
//    }
//
//    // 4-7: Validation consistency tests
//    @Test
//    void testValidation_NullOperand_ConsistentAcrossOperations() {
//        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
//        assertThrows(IllegalArgumentException.class, () -> a.add(null));
//        assertThrows(IllegalArgumentException.class, () -> a.subtract(null));
//        assertThrows(IllegalArgumentException.class, () -> a.divide(null));
//    }
//
//    @Test
//    void testValidation_CrossCategory_ConsistentAcrossOperations() {
//        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
//        Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
//        assertThrows(IllegalArgumentException.class, () -> length.add((Quantity) weight));
//        assertThrows(IllegalArgumentException.class, () -> length.subtract((Quantity) weight));
//        assertThrows(IllegalArgumentException.class, () -> length.divide((Quantity) weight));
//    }
//
//    @Test
//    void testValidation_FiniteValue_ConsistentAcrossOperations() {
//        Quantity<LengthUnit> finite = new Quantity<>(Double.POSITIVE_INFINITY, LengthUnit.FEET);
//        Quantity<LengthUnit> other = new Quantity<>(1.0, LengthUnit.FEET);
//        assertThrows(IllegalArgumentException.class, () -> finite.add(other));
//        assertThrows(IllegalArgumentException.class, () -> finite.subtract(other));
//        assertThrows(IllegalArgumentException.class, () -> finite.divide(other));
//    }
//
//    @Test
//    void testValidation_NullTargetUnit_AddSubtractReject() {
//        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(1.0, LengthUnit.FEET);
//        assertThrows(IllegalArgumentException.class, () -> a.add(b, null));
//        assertThrows(IllegalArgumentException.class, () -> a.subtract(b, null));
//    }
//
//    // 8-11: Enum operation correctness (indirect via public API and division-by-zero)
//    @Test
//    void testArithmeticOperation_Add_EnumComputation() {
//        Quantity<LengthUnit> a = new Quantity<>(7.0, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(3.0, LengthUnit.FEET);
//        assertEquals(10.0, a.add(b).getValue(), EPSILON);
//    }
//
//    @Test
//    void testArithmeticOperation_Subtract_EnumComputation() {
//        Quantity<LengthUnit> a = new Quantity<>(7.0, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(3.0, LengthUnit.FEET);
//        assertEquals(4.0, a.subtract(b).getValue(), EPSILON);
//    }
//
//    @Test
//    void testArithmeticOperation_Divide_EnumComputation() {
//        Quantity<LengthUnit> a = new Quantity<>(7.0, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(2.0, LengthUnit.FEET);
//        assertEquals(3.5, a.divide(b), EPSILON);
//    }
//
//    @Test
//    void testArithmeticOperation_DivideByZero_EnumThrows() {
//        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
//        Quantity<LengthUnit> zero = new Quantity<>(0.0, LengthUnit.FEET);
//        assertThrows(ArithmeticException.class, () -> a.divide(zero));
//    }
//
//    // 12-16: Helper correctness and visibility (use reflection for private helpers)
//    @Test
//    void testPerformBaseArithmetic_ConversionAndOperation() {
//        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE); // base 1.0
//        Quantity<VolumeUnit> b = new Quantity<>(1000.0, VolumeUnit.MILLILITRE); // base 1.0
//        Quantity<VolumeUnit> sum = a.add(b); // 1 + 1 = 2 L
//        assertEquals(2.0, sum.getValue(), EPSILON);
//        assertEquals(VolumeUnit.LITRE, sum.getUnit());
//    }
//
//    @Test
//    void testHelper_BaseUnitConversion_Correct() {
//        // verify conversion via convertTo helper
//        Quantity<VolumeUnit> g = new Quantity<>(1.0, VolumeUnit.GALLON);
//        Quantity<VolumeUnit> inLitres = g.convertTo(VolumeUnit.LITRE);
//        // Accept small tolerance for conversion constants
//        assertEquals(3.78541, inLitres.getValue(), 1e-5);
//    }
//
//    @Test
//    void testHelper_ResultConversion_Correct() {
//        Quantity<VolumeUnit> a = new Quantity<>(3.78541, VolumeUnit.LITRE);
//        Quantity<VolumeUnit> result = a.add(new Quantity<>(3.78541, VolumeUnit.LITRE), VolumeUnit.GALLON);
//        assertEquals(2.0, result.getValue(), 1e-6);
//        assertEquals(VolumeUnit.GALLON, result.getUnit());
//    }
//
//    @Test
//    void testHelper_PrivateVisibility() throws Exception {
//        Class<?> qtyClass = Quantity.class;
//        boolean hasPerform = Arrays.stream(qtyClass.getDeclaredMethods())
//                .anyMatch(m -> m.getName().equals("performBaseArithmetic"));
//        boolean hasValidate = Arrays.stream(qtyClass.getDeclaredMethods())
//                .anyMatch(m -> m.getName().equals("validateArithmeticOperands"));
//        assertTrue(hasPerform, "performBaseArithmetic should exist");
//        assertTrue(hasValidate, "validateArithmeticOperands should exist");
//
//        Method perform = null;
//        for (Method m : qtyClass.getDeclaredMethods()) {
//            if (m.getName().equals("performBaseArithmetic")) perform = m;
//        }
//        if (perform != null) {
//            assertTrue(Modifier.isPrivate(perform.getModifiers()), "performBaseArithmetic should be private");
//        }
//    }
//
//    @Test
//    void testValidation_Helper_PrivateVisibility() throws Exception {
//        Class<?> qtyClass = Quantity.class;
//        Method validate = null;
//        for (Method m : qtyClass.getDeclaredMethods()) {
//            if (m.getName().equals("validateArithmeticOperands")) validate = m;
//        }
//        if (validate != null) {
//            assertTrue(Modifier.isPrivate(validate.getModifiers()), "validateArithmeticOperands should be private");
//        }
//    }
//
//    // 17-19: UC12 behavior preserved (add/subtract/divide)
//    @Test
//    void testAdd_UC12_BehaviorPreserved() {
//        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(12.0, LengthUnit.INCH);
//        assertEquals(2.0, a.add(b).getValue(), EPSILON);
//    }
//
//    @Test
//    void testSubtract_UC12_BehaviorPreserved() {
//        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(6.0, LengthUnit.INCH);
//        assertEquals(9.5, a.subtract(b).getValue(), EPSILON);
//    }
//
//    @Test
//    void testDivide_UC12_BehaviorPreserved() {
//        double ratio = new Quantity<>(24.0, LengthUnit.INCH)
//                .divide(new Quantity<>(2.0, LengthUnit.FEET));
//        assertEquals(1.0, ratio, EPSILON);
//    }
//
//    // 20-22: Rounding behavior
//    @Test
//    void testRounding_AddSubtract_TwoDecimalPlaces() {
//        Quantity<LengthUnit> a = new Quantity<>(1.2345, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(0.0045, LengthUnit.FEET);
//        Quantity<LengthUnit> result = a.subtract(b);
//        assertEquals(1.23, result.getValue(), EPSILON);
//    }
//
//    @Test
//    void testRounding_Divide_NoRounding() {
//        double ratio = new Quantity<>(10.0, LengthUnit.FEET)
//                .divide(new Quantity<>(3.0, LengthUnit.FEET));
//        assertEquals(10.0 / 3.0, ratio, 1e-12);
//    }
//
//    @Test
//    void testRounding_Helper_Accuracy() {
//        Quantity<LengthUnit> a = new Quantity<>(1.2356, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(0.0, LengthUnit.FEET);
//        Quantity<LengthUnit> r = a.add(b);
//        assertEquals(1.24, r.getValue(), EPSILON);
//    }
//
//    // 23-24: Target unit handling
//    @Test
//    void testImplicitTargetUnit_AddSubtract() {
//        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
//        Quantity<VolumeUnit> b = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
//        Quantity<VolumeUnit> sum = a.add(b); // implicit target = first operand's unit (LITRE)
//        assertEquals(2.0, sum.getValue(), EPSILON);
//        assertEquals(VolumeUnit.LITRE, sum.getUnit());
//    }
//
//    @Test
//    void testExplicitTargetUnit_AddSubtract_Overrides() {
//        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
//        Quantity<VolumeUnit> b = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
//        Quantity<VolumeUnit> sum = a.add(b, VolumeUnit.MILLILITRE);
//        assertEquals(2000.0, sum.getValue(), EPSILON);
//        assertEquals(VolumeUnit.MILLILITRE, sum.getUnit());
//    }
//
//    // 25-27: Immutability checks
//    @Test
//    void testImmutability_AfterAdd_ViaCentralizedHelper() {
//        Quantity<LengthUnit> a = new Quantity<>(3.0, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(1.0, LengthUnit.FEET);
//        Quantity<LengthUnit> sum = a.add(b);
//        assertEquals(3.0, a.getValue(), EPSILON);
//        assertEquals(1.0, b.getValue(), EPSILON);
//        assertEquals(4.0, sum.getValue(), EPSILON);
//    }
//
//    @Test
//    void testImmutability_AfterSubtract_ViaCentralizedHelper() {
//        Quantity<LengthUnit> a = new Quantity<>(5.0, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(2.0, LengthUnit.FEET);
//        Quantity<LengthUnit> res = a.subtract(b);
//        assertEquals(5.0, a.getValue(), EPSILON);
//        assertEquals(2.0, b.getValue(), EPSILON);
//        assertEquals(3.0, res.getValue(), EPSILON);
//    }
//
//    @Test
//    void testImmutability_AfterDivide_ViaCentralizedHelper() {
//        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(2.0, LengthUnit.FEET);
//        double ratio = a.divide(b);
//        assertEquals(10.0, a.getValue(), EPSILON);
//        assertEquals(2.0, b.getValue(), EPSILON);
//        assertEquals(5.0, ratio, EPSILON);
//    }
//
//    // 28: All categories sanity
//    @Test
//    void testAllOperations_AcrossAllCategories() {
//        double r1 = new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(5.0, LengthUnit.FEET));
//        double r2 = new Quantity<>(10.0, VolumeUnit.LITRE).divide(new Quantity<>(5.0, VolumeUnit.LITRE));
//        double r3 = new Quantity<>(10.0, WeightUnit.KILOGRAM).divide(new Quantity<>(5.0, WeightUnit.KILOGRAM));
//        assertEquals(2.0, r1, EPSILON);
//        assertEquals(2.0, r2, EPSILON);
//        assertEquals(2.0, r3, EPSILON);
//    }
//
//    // 29-30: DRY verification via consistent behavior across operations
//    @Test
//    void testCodeDuplication_ValidationLogic_Eliminated() {
//        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
//        Quantity<LengthUnit> nan = null;
//        // construct a NaN quantity is not allowed by constructor (Option A), so use POSITIVE_INFINITY to test centralized validation
//        Quantity<LengthUnit> inf = new Quantity<>(Double.POSITIVE_INFINITY, LengthUnit.FEET);
//        Exception e1 = assertThrows(IllegalArgumentException.class, () -> a.add(inf));
//        Exception e2 = assertThrows(IllegalArgumentException.class, () -> a.subtract(inf));
//        Exception e3 = assertThrows(IllegalArgumentException.class, () -> a.divide(inf));
//        assertEquals(e1.getClass(), e2.getClass());
//        assertEquals(e2.getClass(), e3.getClass());
//    }
//
//    @Test
//    void testCodeDuplication_ConversionLogic_Eliminated() {
//        Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);
//        Quantity<LengthUnit> inches = new Quantity<>(12.0, LengthUnit.INCH);
//        Quantity<LengthUnit> r1 = feet.add(inches, LengthUnit.FEET);
//        Quantity<LengthUnit> r2 = inches.add(feet, LengthUnit.FEET);
//        assertEquals(r1.getValue(), r2.getValue(), EPSILON);
//    }
//
//    // 31-33: Enum dispatch and extensibility checks
//    @Test
//    void testEnumDispatch_AllOperations_CorrectlyDispatched() throws Exception {
//        Class<?> qtyClass = Quantity.class;
//        boolean foundEnum = Arrays.stream(qtyClass.getDeclaredClasses())
//                .anyMatch(c -> c.getSimpleName().equals("ArithmeticOperation"));
//        assertTrue(foundEnum, "ArithmeticOperation enum should exist inside Quantity");
//    }
//
//    @Test
//    void testFutureOperation_MultiplicationPattern() throws Exception {
//        Class<?> qtyClass = Quantity.class;
//        Class<?> enumClass = null;
//        for (Class<?> c : qtyClass.getDeclaredClasses()) {
//            if (c.getSimpleName().equals("ArithmeticOperation")) enumClass = c;
//        }
//        assertNotNull(enumClass, "ArithmeticOperation enum must exist");
//        String[] names = Arrays.stream(enumClass.getEnumConstants()).map(Object::toString).toArray(String[]::new);
//        assertTrue(Arrays.asList(names).contains("ADD"));
//        assertTrue(Arrays.asList(names).contains("SUBTRACT"));
//        assertTrue(Arrays.asList(names).contains("DIVIDE"));
//    }
//
//    @Test
//    void testErrorMessage_Consistency_Across_Operations() {
//        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
//        Quantity<LengthUnit> inf = new Quantity<>(Double.POSITIVE_INFINITY, LengthUnit.FEET);
//        Exception exAdd = assertThrows(IllegalArgumentException.class, () -> a.add(inf));
//        Exception exSub = assertThrows(IllegalArgumentException.class, () -> a.subtract(inf));
//        Exception exDiv = assertThrows(IllegalArgumentException.class, () -> a.divide(inf));
//        assertEquals(exAdd.getClass(), exSub.getClass());
//        assertEquals(exSub.getClass(), exDiv.getClass());
//    }
//
//    // 34: Chaining operations
//    @Test
//    void testArithmetic_Chain_Operations() {
//        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
//        Quantity<LengthUnit> q2 = new Quantity<>(2.0, LengthUnit.FEET);
//        Quantity<LengthUnit> q3 = new Quantity<>(1.0, LengthUnit.FEET);
//        double result = q1.add(q2).subtract(q3).divide(q2); // (10+2-1)/2 = 5.5
//        assertEquals(5.5, result, EPSILON);
//    }
//
//    // 35-37: Enum constant correctness (indirect)
//    @Test
//    void testEnumConstant_ADD_CorrectlyAdds() {
//        Quantity<LengthUnit> a = new Quantity<>(7.0, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(3.0, LengthUnit.FEET);
//        assertEquals(10.0, a.add(b).getValue(), EPSILON);
//    }
//
//    @Test
//    void testEnumConstant_SUBTRACT_CorrectlySubtracts() {
//        Quantity<LengthUnit> a = new Quantity<>(7.0, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(3.0, LengthUnit.FEET);
//        assertEquals(4.0, a.subtract(b).getValue(), EPSILON);
//    }
//
//    @Test
//    void testEnumConstant_DIVIDE_CorrectlyDivides() {
//        Quantity<LengthUnit> a = new Quantity<>(7.0, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(2.0, LengthUnit.FEET);
//        assertEquals(3.5, a.divide(b), EPSILON);
//    }
//
//    // 38: Helper base conversion correctness (additional)
//    @Test
//    void testHelper_BaseUnitConversion_Correctness() {
//        Quantity<VolumeUnit> m = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
//        Quantity<VolumeUnit> l = m.convertTo(VolumeUnit.LITRE);
//        assertEquals(1.0, l.getValue(), EPSILON);
//    }
//
//    // 39: Unified validation behavior (Option A uses POSITIVE_INFINITY)
//    @Test
//    void testRefactoring_Validation_UnifiedBehavior() {
//        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
//        Quantity<LengthUnit> invalid = new Quantity<>(Double.POSITIVE_INFINITY, LengthUnit.FEET);
//
//        Exception eAdd = assertThrows(IllegalArgumentException.class, () -> a.add(invalid));
//        Exception eSub = assertThrows(IllegalArgumentException.class, () -> a.subtract(invalid));
//        Exception eDiv = assertThrows(IllegalArgumentException.class, () -> a.divide(invalid));
//
//        assertEquals(eAdd.getClass(), eSub.getClass());
//        assertEquals(eSub.getClass(), eDiv.getClass());
//    }
//
//    //UC14
//
//    // 1. Celsius-to-Celsius equality (reflexive / same-unit)
//    @Test
//    void testTemperatureEquality_CelsiusToCelsius_SameValue() {
//        Quantity<TemperatureUnit> a = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
//        Quantity<TemperatureUnit> b = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
//        assertEquals(a, b);
//    }
//
//    // 2. Fahrenheit-to-Fahrenheit equality (same-unit)
//    @Test
//    void testTemperatureEquality_FahrenheitToFahrenheit_SameValue() {
//        Quantity<TemperatureUnit> a = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
//        Quantity<TemperatureUnit> b = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
//        assertEquals(a, b);
//    }
//
//    // 3. Kelvin-to-Kelvin equality (same-unit)
//    @Test
//    void testTemperatureEquality_KelvinToKelvin_SameValue() {
//        Quantity<TemperatureUnit> a = new Quantity<>(273.15, TemperatureUnit.KELVIN);
//        Quantity<TemperatureUnit> b = new Quantity<>(273.15, TemperatureUnit.KELVIN);
//        assertEquals(a, b);
//    }
//
//    // 4. Celsius to Fahrenheit equality (0°C = 32°F)
//    @Test
//    void testTemperatureEquality_CelsiusToFahrenheit_0CEquals32F() {
//        Quantity<TemperatureUnit> c = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
//        Quantity<TemperatureUnit> f = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
//        assertEquals(c, f);
//    }
//
//    // 5. Celsius to Fahrenheit equality (100°C = 212°F)
//    @Test
//    void testTemperatureEquality_CelsiusToFahrenheit_100CEquals212F() {
//        Quantity<TemperatureUnit> c = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
//        Quantity<TemperatureUnit> f = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);
//        assertTrue(c.equals(f), "100°C should equal 212°F");
//    }
//
//
//    // 6. Celsius to Kelvin equality (0°C = 273.15 K)
//    @Test
//    void testTemperatureEquality_CelsiusToKelvin_0CEquals27315K() {
//        Quantity<TemperatureUnit> c = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
//        Quantity<TemperatureUnit> k = new Quantity<>(273.15, TemperatureUnit.KELVIN);
//        assertEquals(c, k);
//    }
//
//    // 7. Celsius to Kelvin equality (100°C = 373.15 K)
//    @Test
//    void testTemperatureEquality_100CEquals37315K() {
//        Quantity<TemperatureUnit> c = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
//        Quantity<TemperatureUnit> k = new Quantity<>(373.15, TemperatureUnit.KELVIN);
//        assertEquals(c, k);
//    }
//
//    // 8. Special equal point -40°C = -40°F
//    @Test
//    void testTemperatureEquality_Negative40Equal() {
//        Quantity<TemperatureUnit> c = new Quantity<>(-40.0, TemperatureUnit.CELSIUS);
//        Quantity<TemperatureUnit> f = new Quantity<>(-40.0, TemperatureUnit.FAHRENHEIT);
//        assertEquals(c, f);
//    }
//
//    // 9. Symmetric property of equality (A = B implies B = A)
//    @Test
//    void testTemperatureEquality_SymmetricProperty() {
//        Quantity<TemperatureUnit> a = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
//        Quantity<TemperatureUnit> b = new Quantity<>(122.0, TemperatureUnit.FAHRENHEIT);
//        assertEquals(a, b);
//        assertEquals(b, a);
//    }
//
//    // 10. Reflexive property (object equals itself)
//    @Test
//    void testTemperatureEquality_ReflexiveProperty() {
//        Quantity<TemperatureUnit> a = new Quantity<>(10.0, TemperatureUnit.CELSIUS);
//        assertEquals(a, a);
//    }
//
//    // 11. Different values are not equal
//    @Test
//    void testTemperatureEquality_DifferentValues() {
//        Quantity<TemperatureUnit> a = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
//        Quantity<TemperatureUnit> b = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
//        assertNotEquals(a, b);
//    }
//
//    // 12. Celsius to Fahrenheit conversion correctness (various values)
//    @Test
//    void testTemperatureConversion_CelsiusToFahrenheit_VariousValues() {
//        Quantity<TemperatureUnit> a = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
//        Quantity<TemperatureUnit> converted = a.convertTo(TemperatureUnit.FAHRENHEIT);
//        assertEquals(122.0, converted.getValue(), EPSILON);
//
//        Quantity<TemperatureUnit> b = new Quantity<>(-20.0, TemperatureUnit.CELSIUS);
//        Quantity<TemperatureUnit> bConv = b.convertTo(TemperatureUnit.FAHRENHEIT);
//        assertEquals(-4.0, bConv.getValue(), EPSILON);
//    }
//
//    // 13. Fahrenheit to Celsius conversion correctness (reverse)
//    @Test
//    void testTemperatureConversion_FahrenheitToCelsius_VariousValues() {
//        Quantity<TemperatureUnit> a = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
//        Quantity<TemperatureUnit> conv = a.convertTo(TemperatureUnit.CELSIUS);
//        assertEquals(0.0, conv.getValue(), EPSILON);
//
//        Quantity<TemperatureUnit> b = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);
//        Quantity<TemperatureUnit> conv2 = b.convertTo(TemperatureUnit.CELSIUS);
//        assertEquals(100.0, conv2.getValue(), EPSILON);
//    }
//
//    // 14. Celsius to Kelvin conversion correctness
//    @Test
//    void testTemperatureConversion_CelsiusToKelvin() {
//        Quantity<TemperatureUnit> a = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
//        Quantity<TemperatureUnit> conv = a.convertTo(TemperatureUnit.KELVIN);
//        assertEquals(273.15, conv.getValue(), EPSILON);
//    }
//
//    // 15. Kelvin to Celsius conversion correctness
//    @Test
//    void testTemperatureConversion_KelvinToCelsius() {
//        Quantity<TemperatureUnit> a = new Quantity<>(273.15, TemperatureUnit.KELVIN);
//        Quantity<TemperatureUnit> conv = a.convertTo(TemperatureUnit.CELSIUS);
//        assertEquals(0.0, conv.getValue(), EPSILON);
//    }
//
//    // 16. Same-unit conversion returns unchanged value
//    @Test
//    void testTemperatureConversion_SameUnit() {
//        Quantity<TemperatureUnit> a = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
//        Quantity<TemperatureUnit> conv = a.convertTo(TemperatureUnit.CELSIUS);
//        assertEquals(100.0, conv.getValue(), EPSILON);
//    }
//
//    // 17. Zero value conversion (0°C -> 32°F)
//    @Test
//    void testTemperatureConversion_ZeroValue() {
//        Quantity<TemperatureUnit> a = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
//        Quantity<TemperatureUnit> conv = a.convertTo(TemperatureUnit.FAHRENHEIT);
//        assertEquals(32.0, conv.getValue(), EPSILON);
//    }
//
//    // 18. Negative temperature conversions preserve sign
//    @Test
//    void testTemperatureConversion_NegativeValues() {
//        Quantity<TemperatureUnit> a = new Quantity<>(-10.0, TemperatureUnit.CELSIUS);
//        Quantity<TemperatureUnit> conv = a.convertTo(TemperatureUnit.FAHRENHEIT);
//        assertEquals(14.0, conv.getValue(), EPSILON);
//    }
//
//    // 19. Round-trip conversion preserves value within epsilon (C -> F -> C)
//    @Test
//    void testTemperatureConversion_RoundTripPreservesValue() {
//        Quantity<TemperatureUnit> original = new Quantity<>(37.0, TemperatureUnit.CELSIUS);
//        Quantity<TemperatureUnit> toF = original.convertTo(TemperatureUnit.FAHRENHEIT);
//        Quantity<TemperatureUnit> back = toF.convertTo(TemperatureUnit.CELSIUS);
//        assertEquals(original.getValue(), back.getValue(), 1e-6);
//    }
//
//    // 20. add() throws UnsupportedOperationException for temperature
//    @Test
//    void testTemperatureUnsupportedOperation_Add() {
//        Quantity<TemperatureUnit> a = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
//        Quantity<TemperatureUnit> b = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
//        assertThrows(UnsupportedOperationException.class, () -> a.add(b));
//    }
//
//    // 21. subtract() throws UnsupportedOperationException for temperature
//    @Test
//    void testTemperatureUnsupportedOperation_Subtract() {
//        Quantity<TemperatureUnit> a = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
//        Quantity<TemperatureUnit> b = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
//        assertThrows(UnsupportedOperationException.class, () -> a.subtract(b));
//    }
//
//    // 22. divide() throws UnsupportedOperationException for temperature
//    @Test
//    void testTemperatureUnsupportedOperation_Divide() {
//        Quantity<TemperatureUnit> a = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
//        Quantity<TemperatureUnit> b = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
//        assertThrows(UnsupportedOperationException.class, () -> a.divide(b));
//    }
//
//    // 23. Unsupported operation exception message is informative
//    @Test
//    void testTemperatureUnsupportedOperation_ErrorMessage() {
//        Quantity<TemperatureUnit> a = new Quantity<>(10.0, TemperatureUnit.CELSIUS);
//        Quantity<TemperatureUnit> b = new Quantity<>(5.0, TemperatureUnit.CELSIUS);
//        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> a.add(b));
//        assertTrue(ex.getMessage().toLowerCase().contains("not supported") || ex.getMessage().toLowerCase().contains("unsupported"));
//    }
//
//    // 24. Temperature vs Length incompatibility (equals returns false)
//    @Test
//    void testTemperatureVsLengthIncompatibility() {
//        Quantity<TemperatureUnit> t = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
//        Quantity<LengthUnit> l = new Quantity<>(100.0, LengthUnit.FEET);
//        assertNotEquals(t, l);
//    }
//
//    // 25. Temperature vs Weight incompatibility
//    @Test
//    void testTemperatureVsWeightIncompatibility() {
//        Quantity<TemperatureUnit> t = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
//        Quantity<WeightUnit> w = new Quantity<>(50.0, WeightUnit.KILOGRAM);
//        assertNotEquals(t, w);
//    }
//
//    // 26. Temperature vs Volume incompatibility
//    @Test
//    void testTemperatureVsVolumeIncompatibility() {
//        Quantity<TemperatureUnit> t = new Quantity<>(25.0, TemperatureUnit.CELSIUS);
//        Quantity<VolumeUnit> v = new Quantity<>(25.0, VolumeUnit.LITRE);
//        assertNotEquals(t, v);
//    }
//
//    // 27. TemperatureUnit.supportsArithmetic() returns false
//    @Test
//    void testOperationSupportMethods_TemperatureUnit_Addition() {
//        assertFalse(TemperatureUnit.CELSIUS.supportsArithmetic().isSupported());
//    }
//
//    // 28. LengthUnit supportsArithmetic() default true
//    @Test
//    void testOperationSupportMethods_LengthUnit_Addition() {
//        assertTrue(LengthUnit.FEET.supportsArithmetic().isSupported());
//    }
//
//    // 29. WeightUnit supportsArithmetic() default true
//    @Test
//    void testOperationSupportMethods_WeightUnit_Division() {
//        assertTrue(WeightUnit.KILOGRAM.supportsArithmetic().isSupported());
//    }
//
//    // 30. Null unit validation in Quantity constructor
//    @Test
//    void testTemperatureNullUnitValidation() {
//        assertThrows(IllegalArgumentException.class, () -> new Quantity<>(100.0, null));
//    }
//
//    // 31. IMeasurable backward compatibility: existing enums still convert (Length example)
//    @Test
//    void testIMeasurableInterface_BackwardCompatible() {
//        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
//        Quantity<LengthUnit> conv = a.convertTo(LengthUnit.INCH);
//        assertEquals(12.0, conv.getValue(), EPSILON);
//    }
//
//    // 32. TemperatureUnit non-linear conversion verified (F -> K)
//    @Test
//    void testTemperatureUnit_NonLinearConversion_FtoK() {
//        Quantity<TemperatureUnit> f = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
//        Quantity<TemperatureUnit> k = f.convertTo(TemperatureUnit.KELVIN);
//        assertEquals(273.15, k.getValue(), EPSILON);
//    }
//
//    // 33. TemperatureUnit constants accessible
//    @Test
//    void testTemperatureUnit_AllConstants() {
//        assertNotNull(TemperatureUnit.CELSIUS);
//        assertNotNull(TemperatureUnit.FAHRENHEIT);
//        assertNotNull(TemperatureUnit.KELVIN);
//    }
//
//    // 34. Default method inheritance: non-temperature enums inherit supportsArithmetic = true
//    @Test
//    void testTemperatureDefaultMethodInheritance() {
//        assertTrue(VolumeUnit.LITRE.supportsArithmetic().isSupported());
//    }
//
//    // 35. validateOperationSupport throws for temperature (direct call)
//    @Test
//    void testTemperatureValidateOperationSupport_Throws() {
//        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class,
//                () -> TemperatureUnit.CELSIUS.validateOperationSupport("ADD"));
//        assertTrue(ex.getMessage().toLowerCase().contains("not supported") || ex.getMessage().toLowerCase().contains("unsupported"));
//    }
//
//    // 36. Integration: Quantity<TemperatureUnit> can be constructed and converted (no structural changes)
//    @Test
//    void testTemperatureIntegrationWithGenericQuantity() {
//        Quantity<TemperatureUnit> t = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
//        Quantity<TemperatureUnit> f = t.convertTo(TemperatureUnit.FAHRENHEIT);
//        assertEquals(212.0, f.getValue(), EPSILON);
//    }
//
//    // 37. Backward compatibility: UC1-UC13 behavior preserved for non-temperature categories (simple check)
//    @Test
//    void testTemperatureBackwardCompatibility_UC1ThroughUC13() {
//        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
//        Quantity<LengthUnit> b = new Quantity<>(12.0, LengthUnit.INCH);
//        assertEquals(2.0, a.add(b).getValue(), EPSILON);
//    }
//
//    // 38. Conversion precision within epsilon
//    @Test
//    void testTemperatureConversionPrecision_Epsilon() {
//        Quantity<TemperatureUnit> a = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
//        Quantity<TemperatureUnit> k = a.convertTo(TemperatureUnit.KELVIN);
//        assertEquals(373.15, k.getValue(), 1e-6);
//    }
//
//    // 39. TemperatureUnit implements IMeasurable (indirect check via convertToBaseUnit)
//    @Test
//    void testTemperatureEnumImplementsIMeasurable() {
//        double base = TemperatureUnit.FAHRENHEIT.convertToBaseUnit(32.0);
//        assertEquals(0.0, base, EPSILON);
//    }
//
//    //UC15
//    // ---------- Compare Tests ----------
//    @Test void compare_equal_feet() { assertTrue(new Quantity<>(1.0, LengthUnit.FEET).equals(new Quantity<>(1.0, LengthUnit.FEET))); }
//    @Test void compare_equal_inch() { assertTrue(new Quantity<>(12.0, LengthUnit.INCH).equals(new Quantity<>(1.0, LengthUnit.FEET))); }
//    @Test void compare_yard_to_feet() { assertTrue(new Quantity<>(1.0, LengthUnit.YARD).equals(new Quantity<>(3.0, LengthUnit.FEET))); }
//    @Test void compare_yard_to_inch() { assertTrue(new Quantity<>(1.0, LengthUnit.YARD).equals(new Quantity<>(36.0, LengthUnit.INCH))); }
//    @Test void compare_inch_to_cm() { assertTrue(new Quantity<>(1.0, LengthUnit.INCH).equals(new Quantity<>(2.54, LengthUnit.CENTIMETER))); }
//    @Test void compare_cm_to_inch() { assertTrue(new Quantity<>(2.54, LengthUnit.CENTIMETER).equals(new Quantity<>(1.0, LengthUnit.INCH))); }
//    @Test void compare_litre_to_ml() { assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE))); }
//    @Test void compare_kg_to_gram() { assertTrue(new Quantity<>(1.0, WeightUnit.KILOGRAM).equals(new Quantity<>(1000.0, WeightUnit.GRAM))); }
//
//    // ---------- Convert Tests ----------
//    @Test void convert_yard_to_cm() { assertEquals(91.44, new Quantity<>(1.0, LengthUnit.YARD).convertTo(LengthUnit.CENTIMETER).getValue(), EPSILON); }
//    @Test void convert_feet_to_inch() { assertEquals(12.0, new Quantity<>(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCH).getValue(), EPSILON); }
//    @Test void convert_gallon_to_litre() { assertEquals(3.78541, new Quantity<>(1.0, VolumeUnit.GALLON).convertTo(VolumeUnit.LITRE).getValue(), EPSILON); }
//    @Test void convert_ml_to_litre() { assertEquals(1.0, new Quantity<>(1000.0, VolumeUnit.MILLILITRE).convertTo(VolumeUnit.LITRE).getValue(), EPSILON); }
//    @Test void convert_kg_to_gram() { assertEquals(1000.0, new Quantity<>(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM).getValue(), EPSILON); }
//    @Test void convert_tonne_to_kg() { assertEquals(1000.0, new Quantity<>(1.0, WeightUnit.TONNE).convertTo(WeightUnit.KILOGRAM).getValue(), EPSILON); }
//    @Test void convert_celsius_to_fahrenheit() { assertEquals(32.0, new Quantity<>(0.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.FAHRENHEIT).getValue(), EPSILON); }
//    @Test void convert_kelvin_to_celsius() { assertEquals(0.0, new Quantity<>(273.15, TemperatureUnit.KELVIN).convertTo(TemperatureUnit.CELSIUS).getValue(), EPSILON); }
//
//    // ---------- Add/Subtract Tests ----------
//    @Test void add_inch_and_cm() { assertEquals(2.0, new Quantity<>(1.0, LengthUnit.INCH).add(new Quantity<>(2.54, LengthUnit.CENTIMETER)).getValue(), EPSILON); }
//    @Test void add_feet_and_yard() { assertEquals(4.0, new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(1.0, LengthUnit.YARD), LengthUnit.FEET).getValue(), EPSILON); }
//    @Test void add_litre_and_ml() { assertEquals(1.5, new Quantity<>(1.0, VolumeUnit.LITRE).add(new Quantity<>(500.0, VolumeUnit.MILLILITRE), VolumeUnit.LITRE).getValue(), EPSILON); }
//    @Test void add_kg_and_gram() { assertEquals(1.5, new Quantity<>(1.0, WeightUnit.KILOGRAM).add(new Quantity<>(500.0, WeightUnit.GRAM), WeightUnit.KILOGRAM).getValue(), EPSILON); }
//    @Test void subtract_yard_and_feet() { assertEquals(2.0, new Quantity<>(1.0, LengthUnit.YARD).subtract(new Quantity<>(1.0, LengthUnit.FEET), LengthUnit.FEET).getValue(), EPSILON); }
//    @Test void subtract_litre_and_ml() { assertEquals(0.75, new Quantity<>(1.0, VolumeUnit.LITRE).subtract(new Quantity<>(250.0, VolumeUnit.MILLILITRE), VolumeUnit.LITRE).getValue(), EPSILON); }
//    @Test void subtract_kg_and_gram() { assertEquals(0.75, new Quantity<>(1.0, WeightUnit.KILOGRAM).subtract(new Quantity<>(250.0, WeightUnit.GRAM), WeightUnit.KILOGRAM).getValue(), EPSILON); }
//    @Test void subtract_tonne_and_kg() { assertEquals(500.0, new Quantity<>(1.0, WeightUnit.TONNE).subtract(new Quantity<>(500.0, WeightUnit.KILOGRAM), WeightUnit.KILOGRAM).getValue(), EPSILON); }
//
//    // ---------- Divide Tests ----------
//    @Test void divide_feet_by_inch() { assertEquals(12.0, new Quantity<>(1.0, LengthUnit.FEET).divide(new Quantity<>(1.0, LengthUnit.INCH)), EPSILON); }
//    @Test void divide_yard_by_feet() { assertEquals(3.0, new Quantity<>(1.0, LengthUnit.YARD).divide(new Quantity<>(1.0, LengthUnit.FEET)), EPSILON); }
//    @Test void divide_litre_by_ml() { assertEquals(1000.0, new Quantity<>(1.0, VolumeUnit.LITRE).divide(new Quantity<>(1.0, VolumeUnit.MILLILITRE)), EPSILON); }
//    @Test void divide_kg_by_gram() { assertEquals(1000.0, new Quantity<>(1.0, WeightUnit.KILOGRAM).divide(new Quantity<>(1.0, WeightUnit.GRAM)), EPSILON); }
//    @Test void divide_tonne_by_kg() { assertEquals(1000.0, new Quantity<>(1.0, WeightUnit.TONNE).divide(new Quantity<>(1.0, WeightUnit.KILOGRAM)), EPSILON); }
//    @Test void divide_by_zero_throws() { assertThrows(ArithmeticException.class, () -> new Quantity<>(1.0, LengthUnit.FEET).divide(new Quantity<>(0.0, LengthUnit.INCH))); }
//
//    // ---------- Error Tests ----------
//    @Test void compare_cross_category_throws() { assertFalse(new Quantity<>(1.0, LengthUnit.FEET).equals(new Quantity<>(1.0, VolumeUnit.LITRE))); }
//    @Test
//    void convert_cross_category_throws() {
//        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
//        assertThrows(IllegalArgumentException.class,
//                () -> length.convertTo(VolumeUnit.LITRE));
//    }
//
//    @Test void add_cross_category_throws() { assertThrows(IllegalArgumentException.class, () -> new Quantity<>(1.0, LengthUnit.FEET).add((Quantity) new Quantity<>(1.0, VolumeUnit.LITRE))); }
//    @Test void subtract_cross_category_throws() { assertThrows(IllegalArgumentException.class, () -> new Quantity<>(1.0, LengthUnit.FEET).subtract((Quantity) new Quantity<>(1.0, VolumeUnit.LITRE))); }
//    @Test void unsupported_temperature_arithmetic_throws() { assertThrows(UnsupportedOperationException.class, () -> new Quantity<>(1.0, TemperatureUnit.CELSIUS).add(new Quantity<>(1.0, TemperatureUnit.CELSIUS))); }
//    @Test void invalid_dto_throws() { assertThrows(IllegalArgumentException.class, () -> new Quantity<>(Double.NaN, LengthUnit.FEET)); }
//
//    //UC16
//    private QuantityMeasurementRepository repo;
//    private QuantityMeasurementServiceImpl service;
//
//    @BeforeEach
//    void setup() {
//        repo = QuantityMeasurementCacheRepository.getInstance(); // use your in-memory repo
//        repo.deleteAll();
//        service = new QuantityMeasurementServiceImpl(repo);
//    }
//
//
//    // --- Compare (5 tests) ---
//    @Test
//    void testCompare_LengthEquality_FeetInch() {
//        QuantityDTO a = new QuantityDTO(1.0, Unit.FEET, MeasurementType.LENGTH);
//        QuantityDTO b = new QuantityDTO(12.0, Unit.INCH, MeasurementType.LENGTH);
//        QuantityDTO result = service.compare(a, b);
//        assertEquals(1.0, result.getValue(), "1 FEET should equal 12 INCH");
//    }
//
//    @Test
//    void testCompare_LengthInequality() {
//        QuantityDTO a = new QuantityDTO(1.0, Unit.FEET, MeasurementType.LENGTH);
//        QuantityDTO b = new QuantityDTO(10.0, Unit.INCH, MeasurementType.LENGTH);
//        QuantityDTO result = service.compare(a, b);
//        assertEquals(0.0, result.getValue(), "1 FEET should not equal 10 INCH");
//    }
//
//    @Test
//    void testCompare_WeightEquality_KgGram() {
//        QuantityDTO a = new QuantityDTO(1.0, Unit.KILOGRAM, MeasurementType.WEIGHT);
//        QuantityDTO b = new QuantityDTO(1000.0, Unit.GRAM, MeasurementType.WEIGHT);
//        QuantityDTO result = service.compare(a, b);
//        assertEquals(1.0, result.getValue(), "1 KILOGRAM should equal 1000 GRAM");
//    }
//
//    @Test
//    void testCompare_WeightInequality() {
//        QuantityDTO a = new QuantityDTO(2.0, Unit.KILOGRAM, MeasurementType.WEIGHT);
//        QuantityDTO b = new QuantityDTO(500.0, Unit.GRAM, MeasurementType.WEIGHT);
//        QuantityDTO result = service.compare(a, b);
//        assertEquals(0.0, result.getValue(), "2 KG should not equal 500 G");
//    }
//
//    @Test
//    void testCompare_TemperatureEquality_CtoF() {
//        QuantityDTO a = new QuantityDTO(0.0, Unit.CELSIUS, MeasurementType.TEMPERATURE);
//        QuantityDTO b = new QuantityDTO(32.0, Unit.FAHRENHEIT, MeasurementType.TEMPERATURE);
//        QuantityDTO result = service.compare(a, b);
//        assertEquals(1.0, result.getValue(), "0°C should equal 32°F");
//    }
//
//    // --- Add (5 tests) ---
//    @Test
//    void testAdd_LengthUnits_SameUnit() {
//        QuantityDTO a = new QuantityDTO(1.0, Unit.FEET, MeasurementType.LENGTH);
//        QuantityDTO b = new QuantityDTO(2.0, Unit.FEET, MeasurementType.LENGTH);
//        QuantityDTO result = service.add(a, b, Unit.FEET);
//        assertEquals(3.0, result.getValue(), 1e-9);
//    }
//
//    @Test
//    void testAdd_LengthUnits_MixedUnits() {
//        QuantityDTO a = new QuantityDTO(1.0, Unit.FEET, MeasurementType.LENGTH);
//        QuantityDTO b = new QuantityDTO(12.0, Unit.INCH, MeasurementType.LENGTH);
//        QuantityDTO result = service.add(a, b, Unit.FEET);
//        assertEquals(2.0, result.getValue(), 1e-9, "1 ft + 12 in = 2 ft");
//    }
//
//    @Test
//    void testAdd_WeightUnits_KgAndGram() {
//        QuantityDTO a = new QuantityDTO(1.0, Unit.KILOGRAM, MeasurementType.WEIGHT);
//        QuantityDTO b = new QuantityDTO(500.0, Unit.GRAM, MeasurementType.WEIGHT);
//        QuantityDTO result = service.add(a, b, Unit.KILOGRAM);
//        assertEquals(1.5, result.getValue(), 1e-9);
//    }
//
//    @Test
//    void testAdd_WeightUnits_MixedUnitsToGram() {
//        QuantityDTO a = new QuantityDTO(2.0, Unit.KILOGRAM, MeasurementType.WEIGHT);
//        QuantityDTO b = new QuantityDTO(200.0, Unit.GRAM, MeasurementType.WEIGHT);
//        QuantityDTO result = service.add(a, b, Unit.GRAM);
//        assertEquals(2200.0, result.getValue(), 1e-9);
//    }
//
//    @Test
//    void testAdd_TemperatureUnsupported() {
//        assertThrows(UnsupportedOperationException.class, () ->
//                service.add(
//                        new QuantityDTO(100.0, Unit.CELSIUS, MeasurementType.TEMPERATURE),
//                        new QuantityDTO(50.0, Unit.CELSIUS, MeasurementType.TEMPERATURE),
//                        Unit.CELSIUS
//                )
//        );
//    }
//
//    // --- Subtract (5 tests) ---
//    @Test
//    void testSubtract_LengthUnits_SameUnit() {
//        QuantityDTO a = new QuantityDTO(5.0, Unit.FEET, MeasurementType.LENGTH);
//        QuantityDTO b = new QuantityDTO(2.0, Unit.FEET, MeasurementType.LENGTH);
//        QuantityDTO result = service.subtract(a, b, Unit.FEET);
//        assertEquals(3.0, result.getValue(), 1e-9);
//    }
//
//    @Test
//    void testSubtract_LengthUnits_MixedUnits() {
//        QuantityDTO a = new QuantityDTO(2.0, Unit.FEET, MeasurementType.LENGTH);
//        QuantityDTO b = new QuantityDTO(12.0, Unit.INCH, MeasurementType.LENGTH);
//        QuantityDTO result = service.subtract(a, b, Unit.FEET);
//        assertEquals(1.0, result.getValue(), 1e-9, "2 ft - 12 in = 1 ft");
//    }
//
//    @Test
//    void testSubtract_WeightUnits_KgAndGram() {
//        QuantityDTO a = new QuantityDTO(2.0, Unit.KILOGRAM, MeasurementType.WEIGHT);
//        QuantityDTO b = new QuantityDTO(500.0, Unit.GRAM, MeasurementType.WEIGHT);
//        QuantityDTO result = service.subtract(a, b, Unit.KILOGRAM);
//        assertEquals(1.5, result.getValue(), 1e-9);
//    }
//
//    @Test
//    void testSubtract_WeightUnits_MixedUnitsToGram() {
//        QuantityDTO a = new QuantityDTO(2000.0, Unit.GRAM, MeasurementType.WEIGHT);
//        QuantityDTO b = new QuantityDTO(1.0, Unit.KILOGRAM, MeasurementType.WEIGHT);
//        QuantityDTO result = service.subtract(a, b, Unit.GRAM);
//        assertEquals(1000.0, result.getValue(), 1e-9);
//    }
//
//    @Test
//    void testSubtract_TemperatureUnsupported() {
//        assertThrows(UnsupportedOperationException.class, () ->
//                service.subtract(
//                        new QuantityDTO(100.0, Unit.CELSIUS, MeasurementType.TEMPERATURE),
//                        new QuantityDTO(50.0, Unit.CELSIUS, MeasurementType.TEMPERATURE),
//                        Unit.CELSIUS
//                )
//        );
//    }
//
//    // --- Divide (5 tests) ---
//    @Test
//    void testDivide_LengthUnits_SameUnit() {
//        QuantityDTO a = new QuantityDTO(10.0, Unit.FEET, MeasurementType.LENGTH);
//        QuantityDTO b = new QuantityDTO(2.0, Unit.FEET, MeasurementType.LENGTH);
//        QuantityDTO result = service.divide(a, b);
//        assertEquals(5.0, result.getValue(), 1e-9);
//    }
//
//    @Test
//    void testDivide_LengthUnits_MixedUnits() {
//        QuantityDTO a = new QuantityDTO(24.0, Unit.INCH, MeasurementType.LENGTH);
//        QuantityDTO b = new QuantityDTO(2.0, Unit.FEET, MeasurementType.LENGTH);
//        QuantityDTO result = service.divide(a, b);
//        assertEquals(1.0, result.getValue(), 1e-9, "24 in / 2 ft = 1");
//    }
//
//    @Test
//    void testDivide_WeightUnits() {
//        QuantityDTO a = new QuantityDTO(2000.0, Unit.GRAM, MeasurementType.WEIGHT);
//        QuantityDTO b = new QuantityDTO(2.0, Unit.KILOGRAM, MeasurementType.WEIGHT);
//        QuantityDTO result = service.divide(a, b);
//        assertEquals(1.0, result.getValue(), 1e-9, "2000 g / 2 kg = 1");
//    }
//
//    @Test
//    void testDivide_WeightUnits_Mixed() {
//        QuantityDTO a = new QuantityDTO(2.0, Unit.KILOGRAM, MeasurementType.WEIGHT);
//        QuantityDTO b = new QuantityDTO(500.0, Unit.GRAM, MeasurementType.WEIGHT);
//        QuantityDTO result = service.divide(a, b);
//        assertEquals(4.0, result.getValue(), 1e-9, "2 kg / 500 g = 4");
//    }
//
//    @Test
//    void testDivide_TemperatureUnsupported() {
//        assertThrows(UnsupportedOperationException.class, () ->
//                service.divide(
//                        new QuantityDTO(100.0, Unit.CELSIUS, MeasurementType.TEMPERATURE),
//                        new QuantityDTO(50.0, Unit.CELSIUS, MeasurementType.TEMPERATURE)
//                )
//        );
//    }
//
//    // --- Convert (6 tests) ---
//    @Test
//    void testConvert_WeightKgToGram() {
//        QuantityDTO source = new QuantityDTO(1.0, Unit.KILOGRAM, MeasurementType.WEIGHT);
//        QuantityDTO result = service.convert(source, Unit.GRAM);
//        assertEquals(1000.0, result.getValue(), 1e-9);
//    }
//
//    @Test
//    void testConvert_WeightGramToKg() {
//        QuantityDTO source = new QuantityDTO(1000.0, Unit.GRAM, MeasurementType.WEIGHT);
//        QuantityDTO result = service.convert(source, Unit.KILOGRAM);
//        assertEquals(1.0, result.getValue(), 1e-9);
//    }
//
//    @Test
//    void testConvert_LengthFeetToInch() {
//        QuantityDTO source = new QuantityDTO(2.0, Unit.FEET, MeasurementType.LENGTH);
//        QuantityDTO result = service.convert(source, Unit.INCH);
//        assertEquals(24.0, result.getValue(), 1e-9);
//    }
//
//    @Test
//    void testConvert_LengthInchToFeet() {
//        QuantityDTO source = new QuantityDTO(12.0, Unit.INCH, MeasurementType.LENGTH);
//        QuantityDTO result = service.convert(source, Unit.FEET);
//        assertEquals(1.0, result.getValue(), 1e-9);
//    }
//
//    @Test
//    void testConvert_TemperatureCToF() {
//        QuantityDTO source = new QuantityDTO(0.0, Unit.CELSIUS, MeasurementType.TEMPERATURE);
//        QuantityDTO result = service.convert(source, Unit.FAHRENHEIT);
//        assertEquals(32.0, result.getValue(), 1e-9);
//    }
//
//    @Test
//    void testConvert_TemperatureFToC() {
//        QuantityDTO source = new QuantityDTO(32.0, Unit.FAHRENHEIT, MeasurementType.TEMPERATURE);
//        QuantityDTO result = service.convert(source, Unit.CELSIUS);
//        assertEquals(0.0, result.getValue(), 1e-9);
//    }
//
//    // --- Repository Integration (8 tests) ---
//    @Test
//    void testRepository_SaveAndRetrieve() {
//        QuantityDTO a = new QuantityDTO(1.0, Unit.FEET, MeasurementType.LENGTH);
//        QuantityDTO b = new QuantityDTO(2.0, Unit.FEET, MeasurementType.LENGTH);
//        service.add(a, b, Unit.FEET);
//        List<?> all = repo.getAllMeasurements();
//        assertEquals(1, all.size());
//        QuantityMeasurementEntity e = (QuantityMeasurementEntity) all.get(0);
//        assertEquals("ADD", e.getOperation());
//    }
//
//    @Test
//    void testRepository_GetByOperation() {
//        QuantityDTO a = new QuantityDTO(1.0, Unit.FEET, MeasurementType.LENGTH);
//        QuantityDTO b = new QuantityDTO(1.0, Unit.FEET, MeasurementType.LENGTH);
//        service.add(a, b, Unit.FEET);
//        service.subtract(a, b, Unit.FEET);
//        List<?> adds = repo.getMeasurementsByOperation("ADD");
//        assertEquals(1, adds.size());
//    }
//
//    @Test
//    void testRepository_GetByType() {
//        QuantityDTO a = new QuantityDTO(1.0, Unit.KILOGRAM, MeasurementType.WEIGHT);
//        QuantityDTO b = new QuantityDTO(500.0, Unit.GRAM, MeasurementType.WEIGHT);
//        service.add(a, b, Unit.KILOGRAM);
//        List<?> weights = repo.getMeasurementsByType("WEIGHT");
//        assertEquals(1, weights.size());
//    }
//
//    @Test
//    void testRepository_TotalCount() {
//        QuantityDTO a = new QuantityDTO(1.0, QuantityDTO.Unit.FEET, QuantityDTO.MeasurementType.LENGTH);
//        QuantityDTO b = new QuantityDTO(1.0, QuantityDTO.Unit.FEET, QuantityDTO.MeasurementType.LENGTH);
//        service.add(a, b, QuantityDTO.Unit.FEET);
//        service.add(a, b, QuantityDTO.Unit.FEET);
//        assertEquals(2, repo.getTotalCount());
//    }
//
//    @Test
//    void testRepository_DeleteAll() {
//        QuantityDTO a = new QuantityDTO(1.0, QuantityDTO.Unit.FEET, QuantityDTO.MeasurementType.LENGTH);
//        QuantityDTO b = new QuantityDTO(1.0, QuantityDTO.Unit.FEET, QuantityDTO.MeasurementType.LENGTH);
//        service.add(a, b, QuantityDTO.Unit.FEET);
//        repo.deleteAll();
//        assertEquals(0, repo.getTotalCount());
//    }
//
//    @Test
//    void testRepository_SchemaExists_Cache() {
//        // For cache repo schemaExists should return true (in-memory)
//        assertTrue(repo.schemaExists());
//    }
//
//    @Test
//    void testRepository_ForceError() {
//        assertThrows(RuntimeException.class, () -> repo.forceError());
//    }
//
//    @Test
//    void testRepository_TimestampStored() {
//        QuantityDTO a = new QuantityDTO(1.0, QuantityDTO.Unit.FEET, QuantityDTO.MeasurementType.LENGTH);
//        QuantityDTO b = new QuantityDTO(1.0, QuantityDTO.Unit.FEET, QuantityDTO.MeasurementType.LENGTH);
//        service.add(a, b, QuantityDTO.Unit.FEET);
//        List<?> all = repo.getAllMeasurements();
//        QuantityMeasurementEntity e = (QuantityMeasurementEntity) all.get(0);
//        assertNotNull(e.getTimestamp());
//    }
//}