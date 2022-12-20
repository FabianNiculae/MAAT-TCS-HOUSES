<script>
	import Topbar from './Topbar.svelte';
	const links_all = {
		house_cup: [
			{
				label: 'House Points',
				url: '/sorting_hat/house_cup/house_points'
			},
			{
				label: 'Points History',
				url: '/sorting_hat/house_cup/points_history'
			},
			{
				label: 'House Settings',
				url: '/sorting_hat/house_cup/house_settings'
			}
		],
		house_requests: [
			{
				label: 'House Requests',
				url: '/sorting_hat/house_requests/house_request'
			},
			{
				label: 'Request History',
				url: '/sorting_hat/house_requests/request_history'
			}
		],
		settings: [
			{
				label: 'Settings',
				url: '/sorting_hat/settings/viewing_permissions'
			}
		],
		user: [
			{
				label: 'Employees',
				url: '/sorting_hat/user/employees'
			},
			{
				label: 'Employees History',
				url: '/sorting_hat/user/employees_history'
			}
		],
		students: [
			{
				label: 'Students',
				url: '/sorting_hat/students/students'
			}
		]
	};
	const getLinks = (url) => {
		const pathname = url.url.pathname;
		const link_name = Object.keys(links_all).find((link_name) => pathname.includes(link_name));
		const links = links_all[link_name];
		return links;
	};
</script>

<Topbar slot="topbar" {getLinks} />
