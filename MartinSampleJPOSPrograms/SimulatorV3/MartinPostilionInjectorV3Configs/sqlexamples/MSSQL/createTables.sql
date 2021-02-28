USE [isomessages]
GO

/****** Object:  Table [dbo].[isomessages]    Script Date: 2/28/2021 9:03:53 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[isomessages](
	[id] [int] NOT NULL,
	[MTI] [varchar](4) NOT NULL,
	[dataelement_4] [char](12) NOT NULL,
	[subelement_127_3] [varchar](20) NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[messageresults]    Script Date: 2/28/2021 9:03:53 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[messageresults](
	[id] [int] NULL,
	[messageTime] [datetime] NULL,
	[message] [varchar](255) NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[pendingmessages]    Script Date: 2/28/2021 9:03:53 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[pendingmessages](
	[id] [int] NOT NULL,
	[isoMessageId] [int] NOT NULL,
	[status] [varchar](20) NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


