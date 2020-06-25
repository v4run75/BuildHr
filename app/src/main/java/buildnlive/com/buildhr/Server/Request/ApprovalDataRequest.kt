package buildnlive.com.buildhr.Server.Request

data class ApprovalDataRequest(
        var userId:String,
        var projectId:String,
        var type:String
)