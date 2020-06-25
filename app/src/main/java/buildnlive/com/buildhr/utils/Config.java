package buildnlive.com.buildhr.utils;

public class Config {


    public static final int REQ_TIME_OUT = 45000; //45 seconds
    public static final String REQ_GET_INVENTORY = "http://www.buildoapp.in/purchase/index.php?r=MobileUser/SendInventory&user_id=[0]&project_id=[1]";
    public static final String REQ_GET_ITEM_INVENTORY = "http://www.buildoapp.in/purchase/index.php?r=MobileUser/SendItemListing&user_id=[0]&project_id=[1]";
    public static final String REQ_SYNC_PROJECT = "http://www.buildoapp.in/purchase/index.php?r=MobileUser/SyncProject&user_id=[0]&project_id=[1]";
    public static final String REQ_LOGIN = "http://www.buildoapp.in/purchase/index.php?r=MobileUser/UserLogin";
    public static final String UpdateStoreRequestDetail = "https://buildnlive.com/app/mobileapp/index.php?r=Approvals/UpdateStoreRequestDetail";
    public static final String UpdateSitePaymentDetail = "https://buildnlive.com/app/mobileapp/index.php?r=Approvals/UpdateSitePaymentDetail";
    public static final String INVENTORY_ITEM_REQUEST = "http://www.buildoapp.in/purchase/index.php?r=MobileUser/InventoryItemRequest";
    public static final String SEND_NOTIFICATIONS = "http://www.buildoapp.in/purchase/index.php?r=MobileUser/SendNotifications&user_id=[0]&project_id=[1]";
    public static final String GET_NOTIFICATIONS = "http://www.buildoapp.in/purchase/index.php?r=MobileUser/GetNotifications";
    public static final String GET_NOTIFICATIONS_COUNT = "http://www.buildoapp.in/purchase/index.php?r=MobileUser/GetNotificationCount";
    public static final String FORGOT_PASSWORD = "http://www.buildoapp.in/purchase/index.php?r=MobileUser/ForgotPassword";
    public static final String RESET_PASSWORD = "http://www.buildoapp.in/purchase/index.php?r=MobileUser/ChangePassword";
    public static final String PREF_NAME = "OGIL";
    public static final String UpdateSubLabourReport = "https://buildnlive.com/app/mobileapp/index.php?r=Approvals/UpdateSubLabourReport";
    public static final String INVENTORY_SEARCH = "http://www.buildoapp.in/purchase/index.php?r=MobileUser/searchItem&project=[1]&user_id=[0]&text=[2]";
    public static final String LOGOOUT = "http://www.buildoapp.in/purchase/index.php?r=MobileUser/UserLogout&user_id=[0]";
    public static final String UPDATE_FCM_KEY = "http://www.buildoapp.in/purchase/index.php?r=MobileUser/UpdateFCMKey";
    public static final String CheckUID = "http://www.buildoapp.in/purchase/index.php?r=MobileUser/CheckUID";
    public static final String GET_APPROVALS = "http://www.buildoapp.in/purchase/index.php?r=MobileUser/GetApprovals";
    public static final String SUBMIT_APPROVALS = "https://buildnlive.com/app/mobileapp/index.php?r=Approval/SubmitApprovals";
    public static final String CHECK_IN = "http://www.buildoapp.in/purchase/index.php?r=MobileAttendence/CheckIn&user_id=[0]&latitude=[1]&longitude=[2]";
    public static final String CHECK_OUT = "http://www.buildoapp.in/purchase/index.php?r=MobileAttendence/CheckOut&user_id=[0]&attendence_id=[1]&latitude=[2]&longitude=[3]";
    public static final String MARK_ABSENT_USER = "http://www.buildoapp.in/purchase/index.php?r=MobileAttendence/MarkAbsentUser&user_id=[0]&latitude=[1]&longitude=[2]&leave_status=[3]";
    public static final String GET_ATTENDANCE = "http://www.buildoapp.in/purchase/index.php?r=MobileAttendence/GetAttendence&user_id=[0]";
    public static final String ViewLeaveRequest = "http://www.buildoapp.in/purchase/index.php?r=MobileUser/ViewLeaveRequest&user_id=[0]";
    public static final String SaveLeaveRequest = "http://www.buildoapp.in/purchase/index.php?r=MobileUser/SaveLeaveRequest";
    public static final String cancelLeaveRequest = "http://www.buildoapp.in/purchase/index.php?r=MobileUser/cancelLeaveRequest&leave_id=[0]";
    public static final String MarkOutDuty = "http://www.buildoapp.in/purchase/index.php?r=MobileAttendence/MarkOutDuty";

}
