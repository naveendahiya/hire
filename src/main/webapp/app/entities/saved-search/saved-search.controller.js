(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('SavedSearchController', SavedSearchController);

    SavedSearchController.$inject = ['$scope', '$state', 'SavedSearch'];

    function SavedSearchController ($scope, $state, SavedSearch) {
        var vm = this;

        vm.savedSearches = [];

        loadAll();

        function loadAll() {
            SavedSearch.query(function(result) {
                vm.savedSearches = result;
            });
        }
    }
})();
