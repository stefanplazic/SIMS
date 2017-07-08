$(document).ready(function()

		{
	$("#logoutLink").click(function(e)
			{
			e.preventDefault();
			//remove cookie
			$.removeCookie("role", { path: '/' });
			$.removeCookie("jwt", { path: '/' });

			//redirect
			window.location.href = "../index.html"
			}
			);

	});