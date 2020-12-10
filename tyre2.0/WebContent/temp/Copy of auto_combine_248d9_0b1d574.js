var e = $(".j_timepicker_start"), t = $(".j_timepicker_end");
e.datetimepicker({
	lang : IOT.datePickerLang,
	format : "Y-m-d H:i:00",
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
	lang : IOT.datePickerLang,
	format : "Y-m-d H:59:59",
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
}), $("#convertSpeed").bind(
		"change",
		function(e) {
			e.preventDefault();
			var t = $(this).val();
			$(".j_km")[0 == t ? "show" : "hide"](),
					$(".j_mile")[1 == t ? "show" : "hide"](), store.set(
							"speedunit", t)
		}).val(store.get("speedunit") || 0).trigger("change"),
		$("#convertTemp").bind(
				"change",
				function(e) {
					e.preventDefault();
					var t = $(this).val();
					$(".j_temp_c")[0 == t ? "show" : "hide"](),
							$(".j_temp_f")[1 == t ? "show" : "hide"](), store
									.set("tempunit", t)
				}).val(store.get("tempunit") || 0).trigger("change"), $(
				"#convertPressure").bind(
				"change",
				function(e) {
					e.preventDefault();
					var t = $(this).val();
					$(".j_pressure_b")[0 == t ? "show" : "hide"](),
							$(".j_pressure_psi")[1 == t ? "show" : "hide"](),
							$(".j_pressure_kpa")[2 == t ? "show" : "hide"](),
							store.set("pressureunit", t)
				}).val(store.get("pressureunit") || 0).trigger("change")