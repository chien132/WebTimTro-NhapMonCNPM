USE [master]
GO
/****** Object:  Database [QLNHATRO]    Script Date: 3/30/2021 7:16:16 PM ******/
CREATE DATABASE [QLNHATRO]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'QLNHATRO', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\QLNHATRO.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'QLNHATRO_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\QLNHATRO_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [QLNHATRO] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [QLNHATRO].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [QLNHATRO] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [QLNHATRO] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [QLNHATRO] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [QLNHATRO] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [QLNHATRO] SET ARITHABORT OFF 
GO
ALTER DATABASE [QLNHATRO] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [QLNHATRO] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [QLNHATRO] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [QLNHATRO] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [QLNHATRO] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [QLNHATRO] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [QLNHATRO] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [QLNHATRO] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [QLNHATRO] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [QLNHATRO] SET  DISABLE_BROKER 
GO
ALTER DATABASE [QLNHATRO] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [QLNHATRO] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [QLNHATRO] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [QLNHATRO] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [QLNHATRO] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [QLNHATRO] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [QLNHATRO] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [QLNHATRO] SET RECOVERY FULL 
GO
ALTER DATABASE [QLNHATRO] SET  MULTI_USER 
GO
ALTER DATABASE [QLNHATRO] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [QLNHATRO] SET DB_CHAINING OFF 
GO
ALTER DATABASE [QLNHATRO] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [QLNHATRO] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [QLNHATRO] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [QLNHATRO] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'QLNHATRO', N'ON'
GO
ALTER DATABASE [QLNHATRO] SET QUERY_STORE = OFF
GO
USE [QLNHATRO]
GO
/****** Object:  Table [dbo].[account]    Script Date: 3/30/2021 7:16:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[account](
	[username] [nvarchar](50) NOT NULL,
	[password] [nvarchar](50) NOT NULL,
	[hoten] [nvarchar](50) NOT NULL,
	[cmnd] [varchar](13) NOT NULL,
	[dienthoai] [varchar](15) NOT NULL,
	[email] [nvarchar](50) NOT NULL,
	[avata] [image] NULL,
	[role] [nvarchar](10) NOT NULL,
	[ngaydangki] [date] NULL,
 CONSTRAINT [PK_account] PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[chutro]    Script Date: 3/30/2021 7:16:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[chutro](
	[chutro] [nvarchar](50) NOT NULL,
	[diachichutro] [nvarchar](max) NULL,
 CONSTRAINT [PK_renter] PRIMARY KEY CLUSTERED 
(
	[chutro] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[comment]    Script Date: 3/30/2021 7:16:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[comment](
	[khachthue] [nvarchar](50) NOT NULL,
	[id] [int] NOT NULL,
	[thoigian] [datetime] NOT NULL,
	[comment] [nvarchar](100) NULL,
	[diem] [float] NOT NULL,
 CONSTRAINT [PK_comment_1] PRIMARY KEY CLUSTERED 
(
	[khachthue] ASC,
	[id] ASC,
	[thoigian] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[diachi]    Script Date: 3/30/2021 7:16:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[diachi](
	[diachi] [nvarchar](50) NOT NULL,
	[duong] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_diachi] PRIMARY KEY CLUSTERED 
(
	[diachi] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[duong]    Script Date: 3/30/2021 7:16:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[duong](
	[duong] [nvarchar](50) NOT NULL,
	[phuong/xa] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_duong] PRIMARY KEY CLUSTERED 
(
	[duong] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[khachthue]    Script Date: 3/30/2021 7:16:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[khachthue](
	[khachthue] [nvarchar](50) NOT NULL,
	[truong/congty] [nvarchar](100) NULL,
	[namsinh] [int] NULL,
	[gioitinh] [int] NULL,
	[quequan] [nvarchar](50) NULL,
 CONSTRAINT [PK_customer] PRIMARY KEY CLUSTERED 
(
	[khachthue] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[lichhen]    Script Date: 3/30/2021 7:16:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[lichhen](
	[khachthue] [nvarchar](50) NOT NULL,
	[id] [int] NOT NULL,
	[thoigian] [datetime] NOT NULL,
	[dongy] [int] NULL,
	[thanhcong] [int] NULL,
 CONSTRAINT [PK_date] PRIMARY KEY CLUSTERED 
(
	[khachthue] ASC,
	[id] ASC,
	[thoigian] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[nhatro]    Script Date: 3/30/2021 7:16:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[nhatro](
	[id] [int] NOT NULL,
	[tieude] [nvarchar](100) NOT NULL,
	[diachi] [nvarchar](50) NOT NULL,
	[sophongchothue] [int] NOT NULL,
	[songuoitrenphong] [int] NOT NULL,
	[sophongcosan] [int] NOT NULL,
	[dientich] [float] NOT NULL,
	[tiencoc] [money] NOT NULL,
	[tienthue] [money] NOT NULL,
	[mota] [nvarchar](max) NULL,
	[diem] [float] NOT NULL,
	[ngaythem] [date] NOT NULL,
	[chutro] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_houseforrent] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[phuong/xa]    Script Date: 3/30/2021 7:16:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[phuong/xa](
	[phuong/xa] [nvarchar](50) NOT NULL,
	[quan/huyen] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_phuong/xa_1] PRIMARY KEY CLUSTERED 
(
	[phuong/xa] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[quan/huyen]    Script Date: 3/30/2021 7:16:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[quan/huyen](
	[quan/huyen] [nvarchar](50) NOT NULL,
	[thanhpho/tinh] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_thanhpho/tinh] PRIMARY KEY CLUSTERED 
(
	[quan/huyen] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[thongbao]    Script Date: 3/30/2021 7:16:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[thongbao](
	[account] [nvarchar](50) NOT NULL,
	[thoigian] [datetime] NOT NULL,
	[thongbao] [nvarchar](100) NOT NULL,
 CONSTRAINT [PK_thongbao] PRIMARY KEY CLUSTERED 
(
	[account] ASC,
	[thoigian] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[truong/congty]    Script Date: 3/30/2021 7:16:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[truong/congty](
	[truong/congty] [nvarchar](100) NOT NULL,
	[diachi] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_truong/congty] PRIMARY KEY CLUSTERED 
(
	[truong/congty] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[comment] ADD  CONSTRAINT [DF_comment_point]  DEFAULT ((50)) FOR [diem]
GO
ALTER TABLE [dbo].[khachthue] ADD  CONSTRAINT [DF_customer_sex]  DEFAULT ((1)) FOR [gioitinh]
GO
ALTER TABLE [dbo].[lichhen] ADD  CONSTRAINT [DF_date_accept]  DEFAULT ((0)) FOR [dongy]
GO
ALTER TABLE [dbo].[lichhen] ADD  CONSTRAINT [DF_date_success]  DEFAULT ((0)) FOR [thanhcong]
GO
ALTER TABLE [dbo].[nhatro] ADD  CONSTRAINT [DF_houseforrent_point]  DEFAULT ((50)) FOR [diem]
GO
ALTER TABLE [dbo].[chutro]  WITH CHECK ADD  CONSTRAINT [FK_renter_account] FOREIGN KEY([chutro])
REFERENCES [dbo].[account] ([username])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[chutro] CHECK CONSTRAINT [FK_renter_account]
GO
ALTER TABLE [dbo].[comment]  WITH CHECK ADD  CONSTRAINT [FK_comment_customer] FOREIGN KEY([khachthue])
REFERENCES [dbo].[khachthue] ([khachthue])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[comment] CHECK CONSTRAINT [FK_comment_customer]
GO
ALTER TABLE [dbo].[comment]  WITH CHECK ADD  CONSTRAINT [FK_comment_houseforrent] FOREIGN KEY([id])
REFERENCES [dbo].[nhatro] ([id])
GO
ALTER TABLE [dbo].[comment] CHECK CONSTRAINT [FK_comment_houseforrent]
GO
ALTER TABLE [dbo].[diachi]  WITH CHECK ADD  CONSTRAINT [FK_diachi_duong] FOREIGN KEY([duong])
REFERENCES [dbo].[duong] ([duong])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[diachi] CHECK CONSTRAINT [FK_diachi_duong]
GO
ALTER TABLE [dbo].[duong]  WITH CHECK ADD  CONSTRAINT [FK_duong_phuong/xa] FOREIGN KEY([phuong/xa])
REFERENCES [dbo].[phuong/xa] ([phuong/xa])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[duong] CHECK CONSTRAINT [FK_duong_phuong/xa]
GO
ALTER TABLE [dbo].[khachthue]  WITH CHECK ADD  CONSTRAINT [FK_customer_account] FOREIGN KEY([khachthue])
REFERENCES [dbo].[account] ([username])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[khachthue] CHECK CONSTRAINT [FK_customer_account]
GO
ALTER TABLE [dbo].[khachthue]  WITH CHECK ADD  CONSTRAINT [FK_khachthue_truong/congty] FOREIGN KEY([truong/congty])
REFERENCES [dbo].[truong/congty] ([truong/congty])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[khachthue] CHECK CONSTRAINT [FK_khachthue_truong/congty]
GO
ALTER TABLE [dbo].[lichhen]  WITH CHECK ADD  CONSTRAINT [FK_date_customer] FOREIGN KEY([khachthue])
REFERENCES [dbo].[khachthue] ([khachthue])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[lichhen] CHECK CONSTRAINT [FK_date_customer]
GO
ALTER TABLE [dbo].[lichhen]  WITH CHECK ADD  CONSTRAINT [FK_date_houseforrent] FOREIGN KEY([id])
REFERENCES [dbo].[nhatro] ([id])
GO
ALTER TABLE [dbo].[lichhen] CHECK CONSTRAINT [FK_date_houseforrent]
GO
ALTER TABLE [dbo].[nhatro]  WITH CHECK ADD  CONSTRAINT [FK_houseforrent_renter] FOREIGN KEY([chutro])
REFERENCES [dbo].[chutro] ([chutro])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[nhatro] CHECK CONSTRAINT [FK_houseforrent_renter]
GO
ALTER TABLE [dbo].[nhatro]  WITH CHECK ADD  CONSTRAINT [FK_nhatro_diachi] FOREIGN KEY([diachi])
REFERENCES [dbo].[diachi] ([diachi])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[nhatro] CHECK CONSTRAINT [FK_nhatro_diachi]
GO
ALTER TABLE [dbo].[phuong/xa]  WITH CHECK ADD  CONSTRAINT [FK_phuong/xa_quan/huyen] FOREIGN KEY([quan/huyen])
REFERENCES [dbo].[quan/huyen] ([quan/huyen])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[phuong/xa] CHECK CONSTRAINT [FK_phuong/xa_quan/huyen]
GO
ALTER TABLE [dbo].[thongbao]  WITH CHECK ADD  CONSTRAINT [FK_thongbao_account] FOREIGN KEY([account])
REFERENCES [dbo].[account] ([username])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[thongbao] CHECK CONSTRAINT [FK_thongbao_account]
GO
ALTER TABLE [dbo].[truong/congty]  WITH CHECK ADD  CONSTRAINT [FK_truong/congty_diachi] FOREIGN KEY([diachi])
REFERENCES [dbo].[diachi] ([diachi])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[truong/congty] CHECK CONSTRAINT [FK_truong/congty_diachi]
GO
ALTER TABLE [dbo].[account]  WITH CHECK ADD  CONSTRAINT [CK_account_role] CHECK  (([role]='admin' OR [role]='khachthue' OR [role]='chutro'))
GO
ALTER TABLE [dbo].[account] CHECK CONSTRAINT [CK_account_role]
GO
ALTER TABLE [dbo].[comment]  WITH CHECK ADD  CONSTRAINT [CK_comment_diem] CHECK  (((0)<[diem] AND [diem]<=(10)))
GO
ALTER TABLE [dbo].[comment] CHECK CONSTRAINT [CK_comment_diem]
GO
ALTER TABLE [dbo].[comment]  WITH CHECK ADD  CONSTRAINT [CK_comment_thoigian] CHECK  (('January 1,2021'<=[thoigian]))
GO
ALTER TABLE [dbo].[comment] CHECK CONSTRAINT [CK_comment_thoigian]
GO
ALTER TABLE [dbo].[khachthue]  WITH CHECK ADD  CONSTRAINT [CK_customer_gioitinh] CHECK  (([gioitinh]=(0) OR [gioitinh]=(1)))
GO
ALTER TABLE [dbo].[khachthue] CHECK CONSTRAINT [CK_customer_gioitinh]
GO
ALTER TABLE [dbo].[khachthue]  WITH CHECK ADD  CONSTRAINT [CK_khachthue_namsinh] CHECK  (((1950)<=[namsinh]))
GO
ALTER TABLE [dbo].[khachthue] CHECK CONSTRAINT [CK_khachthue_namsinh]
GO
ALTER TABLE [dbo].[lichhen]  WITH CHECK ADD  CONSTRAINT [CK_lichen_thanhcong] CHECK  (([thanhcong]=(0) OR [thanhcong]=(1)))
GO
ALTER TABLE [dbo].[lichhen] CHECK CONSTRAINT [CK_lichen_thanhcong]
GO
ALTER TABLE [dbo].[lichhen]  WITH CHECK ADD  CONSTRAINT [CK_lichhen_dongy] CHECK  (([dongy]=(1) OR [dongy]=(0)))
GO
ALTER TABLE [dbo].[lichhen] CHECK CONSTRAINT [CK_lichhen_dongy]
GO
ALTER TABLE [dbo].[nhatro]  WITH CHECK ADD  CONSTRAINT [CK_houseforrent_diem] CHECK  (((0)<[diem] AND [diem]<=(10)))
GO
ALTER TABLE [dbo].[nhatro] CHECK CONSTRAINT [CK_houseforrent_diem]
GO
ALTER TABLE [dbo].[nhatro]  WITH CHECK ADD  CONSTRAINT [CK_houseforrent_dientich] CHECK  (((0)<=[dientich]))
GO
ALTER TABLE [dbo].[nhatro] CHECK CONSTRAINT [CK_houseforrent_dientich]
GO
ALTER TABLE [dbo].[nhatro]  WITH CHECK ADD  CONSTRAINT [CK_houseforrent_id] CHECK  (([id]>(0)))
GO
ALTER TABLE [dbo].[nhatro] CHECK CONSTRAINT [CK_houseforrent_id]
GO
ALTER TABLE [dbo].[nhatro]  WITH CHECK ADD  CONSTRAINT [CK_houseforrent_songuoitrenphong] CHECK  (((0)<=[songuoitrenphong]))
GO
ALTER TABLE [dbo].[nhatro] CHECK CONSTRAINT [CK_houseforrent_songuoitrenphong]
GO
ALTER TABLE [dbo].[nhatro]  WITH CHECK ADD  CONSTRAINT [CK_houseforrent_sophongchothue] CHECK  (((0)<=[sophongchothue]))
GO
ALTER TABLE [dbo].[nhatro] CHECK CONSTRAINT [CK_houseforrent_sophongchothue]
GO
ALTER TABLE [dbo].[nhatro]  WITH CHECK ADD  CONSTRAINT [CK_houseforrent_sophongcosan] CHECK  (((0)<=[sophongcosan] AND [sophongcosan]<=[sophongchothue]))
GO
ALTER TABLE [dbo].[nhatro] CHECK CONSTRAINT [CK_houseforrent_sophongcosan]
GO
ALTER TABLE [dbo].[nhatro]  WITH CHECK ADD  CONSTRAINT [CK_nhatro_ngaythem] CHECK  (([ngaythem]>'January 1,1970'))
GO
ALTER TABLE [dbo].[nhatro] CHECK CONSTRAINT [CK_nhatro_ngaythem]
GO
ALTER TABLE [dbo].[nhatro]  WITH CHECK ADD  CONSTRAINT [CK_nhatro_tiencoc] CHECK  (((100000)<=[tiencoc]))
GO
ALTER TABLE [dbo].[nhatro] CHECK CONSTRAINT [CK_nhatro_tiencoc]
GO
ALTER TABLE [dbo].[nhatro]  WITH CHECK ADD  CONSTRAINT [CK_nhatro_tienthue] CHECK  (((100000)<=[tienthue]))
GO
ALTER TABLE [dbo].[nhatro] CHECK CONSTRAINT [CK_nhatro_tienthue]
GO
ALTER TABLE [dbo].[thongbao]  WITH CHECK ADD  CONSTRAINT [CK_thongbao_thoigian] CHECK  (('January 1,2021'<=[thoigian]))
GO
ALTER TABLE [dbo].[thongbao] CHECK CONSTRAINT [CK_thongbao_thoigian]
GO
USE [master]
GO
ALTER DATABASE [QLNHATRO] SET  READ_WRITE 
GO
