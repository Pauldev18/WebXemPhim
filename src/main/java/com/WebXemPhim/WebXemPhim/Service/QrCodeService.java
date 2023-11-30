package com.WebXemPhim.WebXemPhim.Service;

public interface QrCodeService {
    byte[] generateQrCode(String qrCodeContent, int width, int height);
}