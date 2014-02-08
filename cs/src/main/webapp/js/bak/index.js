/**
 * User: lidl
 * Date: 13-11-1
 * Time: 下午2:25
 */

var index=(function(){
    return{
        platformStat:function(){
            platformStatLoad();
        }
    }
    function platformStatLoad(){
        $.ajax({
            url:"/ps/msg/platformStat",
            success:function(data){
                if(data.status=='500'){
                    alert(data.exception);
                }else{
                    drawStatBlock(data);
                }
            },
            dataType:"json",
            type:"GET"
        });
    }
    function drawStatBlock(data){
        $("#stat_workerCount").text(data.workerCount);
        $("#stat_totalAmount").text(data.totalAmount);
        $("#stat_totalTask").text(data.totalTask);
    }
})();

$().ready(function(){
    index.platformStat();
});