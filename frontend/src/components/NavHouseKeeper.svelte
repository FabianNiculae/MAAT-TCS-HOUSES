<script>
	import Nav from './Nav.svelte';
	const color = 'bg-sorting_hat-secondary';
	const links = [
		{
			url: '/house_keeper/students/students',
			svg: 'students',
			label: 'Students'
		},
		{
			url: '/house_keeper/user/employees',
			svg: 'employees',
			label: 'Staff'
		},
		{
			url: '/house_keeper/house_requests/house_request',
			svg: 'house_requests',
			label: 'House Request'
		},
		{
			url: '/house_keeper/house_cup/house_points',
			svg: 'house_cup',
			label: 'House Cup'
		},
		{
			url: '/house_keeper/settings/viewing_permissions',
			svg: 'settings',
			label: 'Settings'
		}
	];
</script>

<Nav
	links={links.map((link) => {
		const tab = link.url.split('/').reverse().slice(1).reverse().join('/');
		const isTab = (pathname) => pathname.url.pathname.includes(tab);
		const bg = (pathname) => (isTab(pathname) ? color : '');
		return { ...link, tab, isTab, bg };
	})}
/>
