if not exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[Org]]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
/****** Object:  Table [dbo].[Org]    Script Date: 2023/3/1 上午 09:20:47 ******/
CREATE TABLE [dbo].[Org](
	[OrgId] [int] IDENTITY(1,1) NOT NULL,
	[OrgName] [nvarchar](100) NOT NULL,
	[Email] [nvarchar](100) NOT NULL,
	[PWD] [nvarchar](100) NOT NULL,
	[isAdmin] [int] NOT NULL,
 CONSTRAINT [PK_Org] PRIMARY KEY CLUSTERED 
(
	[OrgId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

ALTER TABLE [dbo].[Org] ADD  CONSTRAINT [DF_Org_OrgName]  DEFAULT ('') FOR [OrgName]
ALTER TABLE [dbo].[Org] ADD  CONSTRAINT [DF_Org_Email]  DEFAULT ('') FOR [Email]
ALTER TABLE [dbo].[Org] ADD  CONSTRAINT [DF_Org_PWD]  DEFAULT ('') FOR [PWD]
ALTER TABLE [dbo].[Org] ADD  CONSTRAINT [DF_Org_isAdmin]  DEFAULT ((0)) FOR [isAdmin]

if not exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[lo_main]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
/****** Object:  Table [dbo].[lo_main]    Script Date: 2023/3/1 上午 09:20:47 ******/
CREATE TABLE [dbo].[lo_main](
	[L_RecId] [int] IDENTITY(1,1) NOT NULL,
	[L_Lottery] [nvarchar](50) NOT NULL,
	[L_Special] [int] NOT NULL,
	[L_BuildOrg] [int] NOT NULL,
	[L_RecDate] [datetime] NOT NULL,
	[L_ModifyOrg] [int] NOT NULL,
	[L_ModifyDate] [datetime] NOT NULL,
	[L_isOpen] [int] NOT NULL,
	[L_getOne] [nvarchar](max) NOT NULL,
	[L_getTwo] [nvarchar](4000) NOT NULL,
	[L_getThree] [nvarchar](4000) NOT NULL,
	[L_getFour] [nvarchar](2000) NOT NULL,
	[L_getFive] [nvarchar](100) NOT NULL,
	[L_getSix] [nvarchar](100) NOT NULL,
 CONSTRAINT [PK_lo_main] PRIMARY KEY CLUSTERED 
(
	[L_RecId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

ALTER TABLE [dbo].[lo_main] ADD  CONSTRAINT [DF_lo_main_L_Lottery]  DEFAULT ('') FOR [L_Lottery]
ALTER TABLE [dbo].[lo_main] ADD  CONSTRAINT [DF_lo_main_L_Special]  DEFAULT ((0)) FOR [L_Special]
ALTER TABLE [dbo].[lo_main] ADD  CONSTRAINT [DF_lo_main_L_BuildOrg]  DEFAULT ((0)) FOR [L_BuildOrg]
ALTER TABLE [dbo].[lo_main] ADD  CONSTRAINT [DF_lo_main_L_RecDate]  DEFAULT (getdate()) FOR [L_RecDate]
ALTER TABLE [dbo].[lo_main] ADD  CONSTRAINT [DF_lo_main_L_ModifyOrg]  DEFAULT ((0)) FOR [L_ModifyOrg]
ALTER TABLE [dbo].[lo_main] ADD  CONSTRAINT [DF_lo_main_L_ModifyDate]  DEFAULT (getdate()) FOR [L_ModifyDate]
ALTER TABLE [dbo].[lo_main] ADD  CONSTRAINT [DF_lo_main_L_isOpen]  DEFAULT ((0)) FOR [L_isOpen]
ALTER TABLE [dbo].[lo_main] ADD  CONSTRAINT [DF_lo_main_L_CountOne]  DEFAULT ('') FOR [L_getOne]
ALTER TABLE [dbo].[lo_main] ADD  CONSTRAINT [DF_lo_main_L_CountTwo]  DEFAULT ('') FOR [L_getTwo]
ALTER TABLE [dbo].[lo_main] ADD  CONSTRAINT [DF_lo_main_L_CountThree]  DEFAULT ('') FOR [L_getThree]
ALTER TABLE [dbo].[lo_main] ADD  CONSTRAINT [DF_lo_main_L_CountFour]  DEFAULT ('') FOR [L_getFour]
ALTER TABLE [dbo].[lo_main] ADD  CONSTRAINT [DF_lo_main_L_CountFive]  DEFAULT ('') FOR [L_getFive]
ALTER TABLE [dbo].[lo_main] ADD  CONSTRAINT [DF_lo_main_L_CountSix]  DEFAULT ('') FOR [L_getSix]

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'彩券號碼 01~49' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'lo_main', @level2type=N'COLUMN',@level2name=N'L_Lottery'
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'特別號 01~08' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'lo_main', @level2type=N'COLUMN',@level2name=N'L_Special'
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1:已開獎 ,0:未開獎' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'lo_main', @level2type=N'COLUMN',@level2name=N'L_isOpen'

if not exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[lo_bet]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
/****** Object:  Table [dbo].[lo_bet]    Script Date: 2023/3/1 上午 09:20:47 ******/
CREATE TABLE [dbo].[lo_bet](
	[B_RecId] [int] IDENTITY(1,1) NOT NULL,
	[B_LRecId] [int] NOT NULL,
	[B_Bet] [nvarchar](50) NOT NULL,
	[B_Special] [int] NOT NULL,
	[B_Win] [nvarchar](50) NOT NULL,
	[B_Winnum] [int] NOT NULL,
	[B_Money] [int] NOT NULL,
	[B_BuildOrg] [int] NOT NULL,
	[B_RecDate] [datetime] NOT NULL,
 CONSTRAINT [PK_lo_bet] PRIMARY KEY CLUSTERED 
(
	[B_RecId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

ALTER TABLE [dbo].[lo_bet] ADD  CONSTRAINT [DF_lo_bet_B_LRecId]  DEFAULT ((0)) FOR [B_LRecId]
ALTER TABLE [dbo].[lo_bet] ADD  CONSTRAINT [DF_lo_bet_B_Bet]  DEFAULT ('') FOR [B_Bet]
ALTER TABLE [dbo].[lo_bet] ADD  CONSTRAINT [DF_lo_bet_B_Special]  DEFAULT ((0)) FOR [B_Special]
ALTER TABLE [dbo].[lo_bet] ADD  CONSTRAINT [DF_lo_bet_B_Win]  DEFAULT ('') FOR [B_Win]
ALTER TABLE [dbo].[lo_bet] ADD  CONSTRAINT [DF_lo_bet_B_Winnum]  DEFAULT ((0)) FOR [B_Winnum]
ALTER TABLE [dbo].[lo_bet] ADD  CONSTRAINT [DF_lo_bet_B_Money]  DEFAULT ((0)) FOR [B_Money]
ALTER TABLE [dbo].[lo_bet] ADD  CONSTRAINT [DF_Table_1_L_BuildOrg]  DEFAULT ((0)) FOR [B_BuildOrg]
ALTER TABLE [dbo].[lo_bet] ADD  CONSTRAINT [DF_lo_bet_B_RecDate]  DEFAULT (getdate()) FOR [B_RecDate]

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'lo_main.L_RecId' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'lo_bet', @level2type=N'COLUMN',@level2name=N'B_LRecId'
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'購買號碼' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'lo_bet', @level2type=N'COLUMN',@level2name=N'B_Bet'
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1: 中特別號 ,0: 沒中特別號' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'lo_bet', @level2type=N'COLUMN',@level2name=N'B_Special'
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'中獎號碼' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'lo_bet', @level2type=N'COLUMN',@level2name=N'B_Win'
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'中獎號碼數' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'lo_bet', @level2type=N'COLUMN',@level2name=N'B_Winnum'
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'購買者' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'lo_bet', @level2type=N'COLUMN',@level2name=N'B_BuildOrg'
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'購買時間' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'lo_bet', @level2type=N'COLUMN',@level2name=N'B_RecDate'

if not exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[lo_classdata]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
/****** Object:  Table [dbo].[lo_classdata]    Script Date: 2023/3/1 上午 09:20:47 ******/
CREATE TABLE [dbo].[lo_classdata](
	[C_RecId] [int] IDENTITY(1,1) NOT NULL,
	[C_ParentId] [int] NOT NULL,
	[C_Name] [nvarchar](50) NOT NULL,
	[C_Number] [int] NOT NULL,
	[C_NO] [nvarchar](100) NOT NULL,
	[C_Sort] [int] NOT NULL,
	[C_isEnable] [int] NOT NULL,
	[C_RecDate] [datetime] NOT NULL,
 CONSTRAINT [PK_lo_classdata] PRIMARY KEY CLUSTERED 
(
	[C_RecId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

ALTER TABLE [dbo].[lo_classdata] ADD  CONSTRAINT [DF_lo_classdata_C_ParentId]  DEFAULT ((0)) FOR [C_ParentId]
ALTER TABLE [dbo].[lo_classdata] ADD  CONSTRAINT [DF_lo_classdata_C_Name]  DEFAULT ('') FOR [C_Name]
ALTER TABLE [dbo].[lo_classdata] ADD  CONSTRAINT [DF_lo_classdata_C_Number]  DEFAULT ((0)) FOR [C_Number]
ALTER TABLE [dbo].[lo_classdata] ADD  CONSTRAINT [DF_Table_1_R_NO]  DEFAULT ('') FOR [C_NO]
ALTER TABLE [dbo].[lo_classdata] ADD  CONSTRAINT [DF_lo_classdata_C_Sort]  DEFAULT ((0)) FOR [C_Sort]
ALTER TABLE [dbo].[lo_classdata] ADD  CONSTRAINT [DF_lo_classdata_C_isEnable]  DEFAULT ((0)) FOR [C_isEnable]
ALTER TABLE [dbo].[lo_classdata] ADD  CONSTRAINT [DF_lo_classdata_C_RecDate]  DEFAULT (getdate()) FOR [C_RecDate]

if not exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[lo_describe]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
/****** Object:  Table [dbo].[lo_describe]    Script Date: 2023/3/1 上午 09:20:47 ******/
CREATE TABLE [dbo].[lo_describe](
	[D_RecId] [int] IDENTITY(1,1) NOT NULL,
	[D_Type] [int] NOT NULL,
	[D_Content] [nvarchar](max) NOT NULL,
	[D_NNStr] [nvarchar](1000) NOT NULL,
	[D_NumberStr] [nvarchar](100) NOT NULL,
	[D_BuildOrg] [int] NOT NULL,
	[D_RecDate] [datetime] NOT NULL,
 CONSTRAINT [PK_lo_describe] PRIMARY KEY CLUSTERED 
(
	[D_RecId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

ALTER TABLE [dbo].[lo_describe] ADD  CONSTRAINT [DF_lo_describe_D_Type]  DEFAULT ((0)) FOR [D_Type]
ALTER TABLE [dbo].[lo_describe] ADD  CONSTRAINT [DF_lo_describe_D_Content]  DEFAULT ('') FOR [D_Content]
ALTER TABLE [dbo].[lo_describe] ADD  CONSTRAINT [DF_lo_describe_D_NNStr]  DEFAULT ('') FOR [D_NNStr]
ALTER TABLE [dbo].[lo_describe] ADD  CONSTRAINT [DF_lo_describe_D_NumberStr]  DEFAULT ('') FOR [D_NumberStr]
ALTER TABLE [dbo].[lo_describe] ADD  CONSTRAINT [DF_lo_describe_D_BuildOrg]  DEFAULT ((0)) FOR [D_BuildOrg]
ALTER TABLE [dbo].[lo_describe] ADD  CONSTRAINT [DF_lo_describe_D_RecDate]  DEFAULT (getdate()) FOR [D_RecDate]

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1:google ,2:en ,3:dream' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'lo_describe', @level2type=N'COLUMN',@level2name=N'D_Type'
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'夢境描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'lo_describe', @level2type=N'COLUMN',@level2name=N'D_Content'
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'名稱+數字' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'lo_describe', @level2type=N'COLUMN',@level2name=N'D_NNStr'
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'夢境解析所產生的數字字串' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'lo_describe', @level2type=N'COLUMN',@level2name=N'D_NumberStr'
