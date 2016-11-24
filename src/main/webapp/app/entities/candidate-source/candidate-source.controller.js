(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateSourceController', CandidateSourceController);

    CandidateSourceController.$inject = ['$scope', '$state', 'CandidateSource'];

    function CandidateSourceController ($scope, $state, CandidateSource) {
        var vm = this;

        vm.candidateSources = [];

        loadAll();

        function loadAll() {
            CandidateSource.query(function(result) {
                vm.candidateSources = result;
            });
        }
    }
})();
