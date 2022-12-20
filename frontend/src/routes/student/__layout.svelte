<script>
	import Header from '../../components/Header.svelte';
	import NavSortingHat from '../../components/NavSortingHat.svelte';
</script>

<div class="flex flex-col h-screen">
	<Header
		options={{
			color: 'main',
			name: 'Yeray Barrios Fleitas',
			role: 'Sorting Hat',
			img: 'https://images.nightcafe.studio//assets/profile.png?tr=w-1600,c-at_max'
		}}
	/>
	<div class="flex grow">
		<NavSortingHat />
		<div class="grow">
			<div>
				<slot name="topbar" />
			</div>
			<div class="">
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
</style>
