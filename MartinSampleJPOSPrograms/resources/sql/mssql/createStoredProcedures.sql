USE [isomessages]
GO

/****** Object:  StoredProcedure [dbo].[getPendingMessages]    Script Date: 2/28/2021 9:04:58 AM ******/
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

/****** Object:  StoredProcedure [dbo].[insertMessageResults]    Script Date: 2/28/2021 9:04:58 AM ******/
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


