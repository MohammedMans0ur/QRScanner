package com.ebe.qrscanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.content.SharedPreferences;

import com.ebe.qrscanner.model.data.source.preferences.SharedManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class SharedManagerTest {

    private SharedManager sharedManager;
    @Mock
    private Context context;
    @Mock
    private SharedPreferences sharedPreferences;
    @Mock
    private SharedPreferences.Editor editor;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Mock the behavior of getSharedPreferences()
        when(context.getSharedPreferences("QR Scanner", Context.MODE_PRIVATE)).thenReturn(sharedPreferences);
        // Mock the behavior of edit()
        when(sharedPreferences.edit()).thenReturn(editor);
        // Mock the behavior of commit()
        when(editor.commit()).thenReturn(true);
        sharedManager = new SharedManager(context);
    }

    @Test
    public void saveAndGetObject_success() {
        // Arrange
        String key = "testObject";
        TestObject testObject = new TestObject("Test Name", 123);

        // Act
        sharedManager.saveObject(testObject, key);
        TestObject retrievedObject = sharedManager.getObject(key, TestObject.class);

        // Assert
        assertEquals(testObject.getName(), retrievedObject.getName());
        assertEquals(testObject.getValue(), retrievedObject.getValue());
    }

    @Test
    public void getObject_returnsNull_whenKeyDoesNotExist() {
        // Arrange
        String key = "nonExistentKey";

        // Act
        TestObject retrievedObject = sharedManager.getObject(key, TestObject.class);

        // Assert
        assertNull(retrievedObject);
    }


    private static class TestObject {
        private String name;
        private int value;

        public TestObject(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }
    }
}