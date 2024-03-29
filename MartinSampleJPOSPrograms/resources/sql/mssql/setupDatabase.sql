USE [master]
GO

/****** Object:  Database [isomessages]    Script Date: 2/28/2021 9:02:56 AM ******/
CREATE DATABASE [isomessages]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'isomessages', FILENAME = N'C:\databases\isomessages.mdf' , SIZE = 4096KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'isomessages_log', FILENAME = N'C:\databases\isomessages_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO

ALTER DATABASE [isomessages] SET COMPATIBILITY_LEVEL = 110
GO

USE [isomessages] 
GO
/****** Object:  Table [dbo].[isomessages]    Script Date: 1/16/2022 8:11:41 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[isomessages](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[MTI] [varchar](4) NOT NULL,
	[dataelement_4] [char](12) NOT NULL,
	[subelement_127_3] [varchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[messageresults]    Script Date: 1/16/2022 8:11:41 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[messageresults](
	[id] [int] NULL,
	[messageTime] [datetime] NULL,
	[message] [varchar](255) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[pendingmessages]    Script Date: 1/16/2022 8:11:41 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[pendingmessages](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[isoMessageId] [int] NOT NULL,
	[status] [varchar](20) NOT NULL,
 CONSTRAINT [PK_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[pendingmessages]  WITH CHECK ADD  CONSTRAINT [FK_isoMessageID_isoMessages] FOREIGN KEY([isoMessageId])
REFERENCES [dbo].[isomessages] ([id])
GO
ALTER TABLE [dbo].[pendingmessages] CHECK CONSTRAINT [FK_isoMessageID_isoMessages]
GO
/****** Object:  StoredProcedure [dbo].[getPendingMessages]    Script Date: 1/16/2022 8:11:41 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		Martin Gutierrez
-- Create date: 02/28/2021
-- Description:	A procedure to grab the values of the system that is pending
-- =============================================
CREATE PROCEDURE [dbo].[getPendingMessages]
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	DECLARE @pendingid int
	SET @pendingid = (SELECT TOP 1 id FROM pendingmessages WHERE status = 'Pending')
	UPDATE pendingmessages SET status = 'Transmitted' WHERE id = @pendingid
	SELECT * FROM isomessages WHERE id = (SELECT isoMessageId FROM pendingmessages WHERE id = @pendingid)
END

GO
/****** Object:  StoredProcedure [dbo].[insertMessageResults]    Script Date: 1/16/2022 8:11:41 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		Martin Gutierrez
-- Create date: 02/28/2021
-- Description:	Insert Results
-- =============================================
CREATE PROCEDURE [dbo].[insertMessageResults]
	-- Add the parameters for the stored procedure here
	@idNumber int, 
	@message varchar(255)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	INSERT INTO messageresults
	(id, messageTime, message)
	VALUES
	(@idNumber, getdate(), @message)
END

GO
