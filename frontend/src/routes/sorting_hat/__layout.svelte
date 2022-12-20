<script>
	import Header from '../../components/Header.svelte';
	import NavSortingHat from '../../components/NavSortingHat.svelte';
	import TopbarSortingHat from '../../components/TopbarSortingHat.svelte';
	import { page } from '$app/stores';
</script>

<div class="flex flex-col h-screen">
	<Header
		options={{
			color: '#1b998b',
			name: 'Yeray Barrios Fleitas',
			role: 'Sorting Hat',
			img: 'https://images.nightcafe.studio//assets/profile.png?tr=w-1600,c-at_max'
		}}
	/>
	<div class="flex grow">
		<NavSortingHat />
		<div class="grow">
			<TopbarSortingHat url={$page.url.pathname} />
			<div class="main p-5">
				<slot />
			</div>
		</div>
	</div>
</div>

<style>
	@tailwind base;
	@tailwind components;
	@tailwind utilities;
	* {
		box-sizing: border-box;
	}
	.main {
		height: calc(99vh - 145px);
		width: calc(100vw - 200px);
	}
</style>
