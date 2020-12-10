var flags=true;
$(function(evts) {
	$.datetimepicker.setLocale('zh');
	var e = $("#startTime"), t = $("#endTime");
	e.datetimepicker({
		lang : 'zh',
		format : "Y-m-d H:i",
		formatDate : "Y-m-d",
		defaultTime : "00:00",
		onShow : function() {
			var e = t.val().split(/\s/)[0] || !1, a = !1;
			if (e) {
				var i = new Date(e);
				i.setMonth(i.getMonth() - 1), a = i.getFullYear() + "-"
						+ (i.getMonth() + 1) + "-" + i.getDate()
			}
			this.setOptions({
				minDate : a,
				maxDate : e
			})
		}
	}), t.datetimepicker({
		lang : 'zh',
		format : "Y-m-d H:59",
		formatTime : "H:59",
		defaultTime : "23:59",
		formatDate : "Y-m-d",
		onShow : function() {
			var t = e.val().split(/\s/)[0] || !1, a = !1;
			if (t) {
				var i = new Date(t);
				i.setMonth(i.getMonth() + 1), a = i.getFullYear() + "-"
						+ (i.getMonth() + 1) + "-" + i.getDate()
			}
			this.setOptions({
				minDate : t,
				maxDate : a
			})
		}
	});
	
	$("a[title='尾页']").hide();
	$(".infoTextAndGoPageBtnWrap").hide();
	
});

function mapdtu(ids){
	location.href="../device/map.action?s="+ids;
}
