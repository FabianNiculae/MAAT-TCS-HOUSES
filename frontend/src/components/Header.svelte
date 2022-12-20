<script>
	export let options;
</script>

<header
	style="background-color: {options.color}"
	class="w-full flex justify-between text-lg text-white"
>
	<div class="flex items-center mx-4 font-bold text-2xl uppercase">{options.role}</div>
	<div class="flex items-center p-3">
		<a class="underline" href="https://google.com">{options.name}</a>
		<img class="w-12 h-12 rounded-3xl mx-3" src={options.img} alt="profile_picture" />
	</div>
</header>
