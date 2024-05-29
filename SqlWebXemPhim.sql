
CREATE DATABASE IF NOT EXISTS FSTARS_test;

USE FSTARS_test;
CREATE TABLE users (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    ten_user VARCHAR(200),
    avatar nvarchar(255),
    sdt nvarchar(25),
    gioi_tinh bit,
    ngay_sinh date,
    tai_khoan VARCHAR(200),
    mat_khau VARCHAR(200),
    gmail VARCHAR(200)
);


CREATE TABLE roles (
    id_role INT AUTO_INCREMENT PRIMARY KEY,
    ten_role VARCHAR(50)
);

CREATE TABLE users_roles (
    id_users_roles INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT NOT NULL,
    id_role INT NOT NULL,
    FOREIGN KEY (id_user) REFERENCES users(id_user),
    FOREIGN KEY (id_role) REFERENCES roles(id_role)
);

CREATE TABLE phim (
    id_phim INT AUTO_INCREMENT PRIMARY KEY,
    ten_phim VARCHAR(200),
    anh_phim longtext,
    the_loai VARCHAR(200),
    thoi_luong TIME,
    khoi_chieu DATETIME,
    dao_dien VARCHAR(200),
    dien_vien VARCHAR(200),
    ngon_ngu VARCHAR(200),
    danh_gia VARCHAR(200),
    noi_dung VARCHAR(1000),
    tinh_trang int
);

CREATE TABLE ngay_chieu (
    id_ngay_chieu INT AUTO_INCREMENT PRIMARY KEY,
    ngay_chieu DATETIME
);

CREATE TABLE tinh (
    id_tinh INT AUTO_INCREMENT PRIMARY KEY,
    tinh VARCHAR(200)
);

CREATE TABLE dia_diem (
    id_dia_diem INT AUTO_INCREMENT PRIMARY KEY,
    dia_chi VARCHAR(200)
);

CREATE TABLE gio_chieu (
    id_gio_chieu INT AUTO_INCREMENT PRIMARY KEY,
    gio_chieu TIME
);

CREATE TABLE loai_rap (
    id_loai_rap INT AUTO_INCREMENT PRIMARY KEY,
    loai_rap VARCHAR(200),
    gia_tien DECIMAL(10, 2)
);

CREATE TABLE cho_ngoi (
    id_cho_ngoi INT AUTO_INCREMENT PRIMARY KEY,
    cho_ngoi nvarchar(200),
    trang_thai int
);

CREATE TABLE suat_chieu (
    id_suat_chieu INT AUTO_INCREMENT PRIMARY KEY,
    id_phim INT NOT NULL,
    id_ngay_chieu INT NOT NULL,
    id_tinh INT NOT NULL,
    id_dia_diem INT NOT NULL,
    id_gio_chieu INT NOT NULL,
    id_loai_rap INT NOT NULL,
    id_cho_ngoi INT NOT NULL,
    FOREIGN KEY (id_phim) REFERENCES phim(id_phim),
    FOREIGN KEY (id_ngay_chieu) REFERENCES ngay_chieu(id_ngay_chieu),
    FOREIGN KEY (id_dia_diem) REFERENCES dia_diem(id_dia_diem),
    FOREIGN KEY (id_gio_chieu) REFERENCES gio_chieu(id_gio_chieu),
    FOREIGN KEY (id_tinh) REFERENCES tinh(id_tinh),
    FOREIGN KEY (id_loai_rap) REFERENCES loai_rap(id_loai_rap),
    FOREIGN KEY (id_cho_ngoi) REFERENCES cho_ngoi(id_cho_ngoi)
);

CREATE TABLE ma_ve (
    id_ma_ve INT AUTO_INCREMENT PRIMARY KEY,
    masove blob,
    trangthai int,
    created_at datetime not null,
    id_suat_chieu int not null,
    FOREIGN KEY (id_suat_chieu) REFERENCES suat_chieu(id_suat_chieu)
);

CREATE TABLE lich_su_dat_ve (
    id_lsdv INT AUTO_INCREMENT PRIMARY KEY,
    id_ma_ve INT NOT NULL,
    ngay_mua date,
    FOREIGN KEY (id_ma_ve) REFERENCES ma_ve(id_ma_ve),
    id_user INT NOT NULL,
    FOREIGN KEY (id_user) REFERENCES users(id_user)
);

create table hoadon(
   id_hoadon int auto_increment,
   primary key(id_hoadon),
   id_user int not null,
   foreign key(id_user) references users(id_user),
   id_suat_chieu int not null,
   foreign key(id_suat_chieu) references suat_chieu(id_suat_chieu),
   total_price double not null,
   created_at datetime not null,
   trang_thai nvarchar(50) not null
);
INSERT INTO roles (ten_role) VALUES
('User'),
('Admin');
