package com.ebe.qrscanner.model.data.source.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ebe.qrscanner.model.data.dto.QRItemDTO;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface QRItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertItem(QRItemDTO QRItemDTO);

    @Query("select * from Items Order by Date DESC")
    List<QRItemDTO> getAllItems();

    @Query("select * from Items where Id = :id")
    QRItemDTO getQRItem(Long id);

    @Query("update Items set Favorite = :favorite where Id = :qrItemId")
    void setFavorite(Long qrItemId, boolean favorite);

    @Query("select * from Items where Favorite = 1 Order by Date DESC")
    List<QRItemDTO> getFavoriteQRItems();
}
