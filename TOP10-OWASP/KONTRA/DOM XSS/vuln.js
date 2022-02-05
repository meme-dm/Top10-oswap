function refreshTab() {
  const category = (new URL(location.href))
    .searchParams.get('filter')
    .replace('+', ' ');

  const activeTabLink = document.querySelector(`.tablink[data-category=${category}]`);

  if (activeTabLink) {
    activeTab.classList.add('active');
  }

  // Search products
  const products = category ? data.filter(product => {
    return product.category === category;
  }) : data;

  // Show current category name
  document.getElementById('currentTabName').innerHTML = category;

  // Show products
  let productsHTML = '';
  products.forEach(product => {
    productsHTML += `
      <div>
        <img src="${product.icon}">
        <p>${product.name}</p>
        <p>${product.description}</p>
      </div>
    `;
  });

  document.getElementById('list').innerHTML = productsHTML;
}


document.addEventListener('DOMContentLoaded', () => {
  const tabLinks = document.getElementsByClassName('tablink');

  tabLinks.forEach(link => {
    link.addEventListener('click', (event) => {
      location.search = `?filter=${event.target.innerText}`;
    });
  });

  refreshTab();
});