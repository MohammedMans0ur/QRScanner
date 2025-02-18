package com.ebe.qrscanner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.ebe.qrscanner.model.DataManager;
import com.ebe.qrscanner.model.data.dto.QRItemDTO;
import com.ebe.qrscanner.model.data.source.database.DatabaseHelper;
import com.ebe.qrscanner.model.data.source.database.QRItemDao;
import com.ebe.qrscanner.ui.favorites.presenter.FavoritesPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class FavoritesPresenterTest {
    @Mock
    private DataManager dataManager;

    @Mock
    private DatabaseHelper databaseHelper;

    @Mock
    private QRItemDao qrItemDao;

    private FavoritesPresenter favoritesPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(databaseHelper.qrItemDao()).thenReturn(qrItemDao);
        when(dataManager.getDatabaseHelper()).thenReturn(databaseHelper);

        favoritesPresenter = new FavoritesPresenter(dataManager);
    }

    @Test
    public void getFavoriteQRItems_returnsCorrectList() {
        List<QRItemDTO> expectedItems = new ArrayList<>();
        expectedItems.add(new QRItemDTO("111111111111", "1", "Bar", false, null));
        expectedItems.add(new QRItemDTO("https://www.wikipedia.org/", "17/2/2025", "QR", false, null));

        when(qrItemDao.getFavoriteQRItems()).thenReturn(expectedItems);

        List<QRItemDTO> actualItems = favoritesPresenter.getFavoriteQRItems();

        assertEquals(expectedItems.size(), actualItems.size());
        assertEquals(expectedItems.get(0).getId(), actualItems.get(0).getId());
        assertEquals(expectedItems.get(1).getId(), actualItems.get(1).getId());
    }

    @Test
    public void getFavoriteQRItems_returnsEmptyList() {
        List<QRItemDTO> expectedItems = new ArrayList<>(); // Empty list
        when(qrItemDao.getFavoriteQRItems()).thenReturn(expectedItems); // Mock DataManager to return an empty list

        List<QRItemDTO> actualItems = favoritesPresenter.getFavoriteQRItems();

        assertEquals(expectedItems.size(), actualItems.size()); // Check if the returned list is empty
    }

    @Test
    public void getFavoriteQRItems_returnsNull() {
        when(qrItemDao.getFavoriteQRItems()).thenReturn(null); // Mock DataManager to return null
        List<QRItemDTO> actualItems = favoritesPresenter.getFavoriteQRItems();
        assertEquals(null, actualItems); // Check if the returned list is null
    }
}