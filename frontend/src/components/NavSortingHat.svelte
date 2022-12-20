<script>
	import Nav from './Nav.svelte';
	const color = 'bg-secondary';
	const links = [
		{
			url: '/sorting_hat/students/students',
			svg: 'students',
			label: 'Students'
		},
		{
			url: '/sorting_hat/user/employees',
			svg: 'employees',
			label: 'Staff'
		},
		{
			url: '/sorting_hat/house_requests/house_request',
			svg: 'house_requests',
			label: 'House Request'
		},
		{
			url: '/sorting_hat/house_cup/house_points',
			svg: 'house_cup',
			label: 'House Cup'
		},
		{
			url: '/sorting_hat/settings/viewing_permissions',
			svg: 'settings',
			label: 'Settings'
		}
	];
</script>

<Nav
	links={
		links.map((link) => {
			const tab = link.url.split('/').reverse().slice(1).reverse().join('/');
      const isTab = (pathname) => (pathname.url.pathname.includes(tab))
      const bg = (pathname) => isTab(pathname) ? color : ''
			return { ...link, tab, isTab, bg };
		})
	}
/>
