package ru.gribnoff.springshop;

import org.junit.Assert;
import org.junit.Test;
import ru.gribnoff.springshop.util.UUIDValidator;

public class UUIDValidatorTest {
    private static final String[] validUUID = {
            "e99929fc-8877-4dc2-ab7b-2dc03230a987",
            "b66f3826-cb9a-46d2-803d-032f63fb5ff7",
            "bbfa832e-78f1-4f77-aebb-8ca93ed895ec",
            "2f6381f1-b507-4093-8981-3e74ec348262",
            "9e732ad3-00ee-4164-ba61-d32c4a1a4551"
    };

    private static final String[] invalidUUID = {
            "c56a-65aa-42ec-a945-5fd21dec",
            "x56t4180-h5aa-42ec-a945-5fd21dec0538",
            "x56t41802-h5aa1-42eyc-a945z-5fd21dec0538",
            "",
            "some string"
    };

    @Test
    public void uuidValidatorTest() {
//        for (String uuid : validUUID)
//            Assert.assertTrue(UUIDValidator.isUUID(uuid));
//        for (String uuid : invalidUUID)
//            Assert.assertFalse(UUIDValidator.isUUID(uuid));
    }

    @Test
    public void uuidValidatorTest1() {
        Assert.assertTrue(UUIDValidator.isUUID(validUUID[0]));
    }

    @Test
    public void uuidValidatorTest2() {
        Assert.assertTrue(UUIDValidator.isUUID(validUUID[1]));
    }

    @Test
    public void uuidValidatorTest3() {
        Assert.assertTrue(UUIDValidator.isUUID(validUUID[2]));
    }

    @Test
    public void uuidValidatorTest4() {
        Assert.assertTrue(UUIDValidator.isUUID(validUUID[3]));
    }

    @Test
    public void uuidValidatorTest5() {
        Assert.assertTrue(UUIDValidator.isUUID(validUUID[4]));
    }

    @Test
    public void uuidValidatorTest6() {
        Assert.assertFalse(UUIDValidator.isUUID(invalidUUID[0]));
    }

    @Test
    public void uuidValidatorTest7() {
        Assert.assertFalse(UUIDValidator.isUUID(invalidUUID[1]));
    }

    @Test
    public void uuidValidatorTest8() {
        Assert.assertFalse(UUIDValidator.isUUID(invalidUUID[2]));
    }

    @Test
    public void uuidValidatorTest9() {
        Assert.assertFalse(UUIDValidator.isUUID(invalidUUID[3]));
    }

    @Test
    public void uuidValidatorTest10() {
        Assert.assertFalse(UUIDValidator.isUUID(invalidUUID[4]));
    }
}
